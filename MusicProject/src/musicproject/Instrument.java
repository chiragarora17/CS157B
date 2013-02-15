/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicproject;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;


/**
 *
 * @author chirag
 */

@Entity
public class Instrument {
    
    private int instrumentid;
    private String name;
    private double cost;
    private int inStock;
    private Description description;
    
    public Instrument(){}
    
    public Instrument(String name, double cost, int inStock, Description description)
    {
        this.name = name;
        this.cost = cost;
        this.inStock = inStock;
        this.description = description;
    }
    
    @Id
    @GeneratedValue
    @Column(name="instrumentid")
    public int getInstrumentId() { return instrumentid; }
    public void setInstrumentId(int instrumentid) { this.instrumentid = instrumentid; }
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Column(name="cost")
    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }
    @Column(name="inStock")
    public int getinStock() { return inStock; }
    public void setinStock(int inStock) { this.inStock = inStock; }
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="descid")
    public Description getDescId() { return description; }
    public void setDescId(Description description) { this.description = description; }
    
    /**
    * Print Instrument attributes.
    */
    private void print()
    {
        System.out.printf("%d: %s %d %d (%s) \n", instrumentid, name, cost,inStock, description.getdescId());
    }
    
     /**
     * Load the Instrument table.
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        Transaction tx = session.beginTransaction();
        {
            session.save(new Instrument("Flute", 200.00,2, new Description("This is flute")));
            session.save(new Instrument("Piccolo", 400.00,2, new Description("This is Piccolo")));
            session.save(new Instrument("Clarinet", 400.00,2, new Description("This is Clarinet")));
            session.save(new Instrument("Basson", 500.00,2, new Description("This is Basson")));
        }
        tx.commit();
        session.close();
        
        System.out.println("Instrument table loaded.");
    }
        
    /**
     * List all the Instruments.
     */
    public static void list()
    {
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Instrument.class);
        criteria.addOrder(Order.asc("instrumentid"));
        
        List<Instrument> instruments = criteria.list();
        System.out.println("All Instruments:");
        
        for (Instrument ins : instruments) {
            ins.print();
        }
        session.close();
    }

     /**
     * Fetch the instrument with a matching name.
     * @param name the name to match.
     * @return the instrument or null.
     */
    public static Instrument find(String Name)
    {
        // Query by example.
        Instrument prototype = new Instrument();
        prototype.setName(Name);
        Example example = Example.create(prototype);  
        
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Instrument.class);
        criteria.add(example);

        Instrument ins = (Instrument) criteria.uniqueResult();
        
        session.close();
        return ins;
    }
    
}
