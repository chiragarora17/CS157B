/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicproject;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
@Table(name="accessories")
public class Accessories {
    
    
    private int accessoryid;
    private String accessoryname;
    private double cost;
    private int inStock;
    private List<Instrument> instruments = new ArrayList<Instrument>();

    public Accessories(){}
    
    public Accessories(String accessoryname, double cost, int inStock)
    {
        this.accessoryname = accessoryname;
        this.cost = cost;
        this.inStock = inStock;
    }

    @Id
    @GeneratedValue
    @Column(name="accessoryid")
    public int getAccessoryId() { return accessoryid; }
    public void setAccessoryId(int accessoryid) { this.accessoryid = accessoryid; }
    @Column(name="name")
    public String getaccessoryName() { return accessoryname; }
    public void setaccessoryName(String accessoryname) { this.accessoryname = accessoryname; }
    @Column(name="cost")
    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }
    @Column(name="inStock")
    public int getinStock() { return inStock; }
    public void setinStock(int inStock) { this.inStock = inStock; }
    @ManyToMany
    @JoinTable(name="Instrument_Accessory", 
               joinColumns={@JoinColumn(name="accessory_code")},
               inverseJoinColumns={@JoinColumn(name="instrument_code")})
    public List<Instrument> getInstrument() { return instruments; }
    public void setInstrument(List<Instrument> instruments) { this.instruments = instruments; }

    /**
     * Print accessories attributes.
     */
    public void print()
    {
        System.out.printf("%d: %s %.2f %d \n", accessoryid, accessoryname, cost,inStock);
    }
    
          
    /**
     * List all the Instruments.
     */
    public static void list()
    {
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Accessories.class);
       // criteria.addOrder(Order.asc("accessoryid"));
        
        List<Accessories> accessory = criteria.list();
        System.out.println("All Accessories:");
        
        for (Accessories acc : accessory) {
            acc.print();
        }
        session.close();
    }
  
    /**
     * Print accessory attributes within a session.
     */
    public void printInSession()
    {
        Session session = HibernateContext.getSession();
        session.update(this);
        print();
        session.close();
    }
    /**
     * Fetch the accessory with a matching name.
     * @param name the name to match.
     * @return the accessory or null.
     */
    public static Accessories find(String Name)
    {
        // Query by example.
        Accessories prototype = new Accessories();
        prototype.setaccessoryName(Name);
        Example example = Example.create(prototype);  
        
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Accessories.class);
        criteria.add(example);

        Accessories acc = (Accessories) criteria.uniqueResult();
        
        session.close();
        return acc;
    }
    
     /**
     * Load the Accessories table.
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        Transaction tx = session.beginTransaction();
        {
            session.save(new Accessories("Flute accessory", 20.00,2));
            session.save(new Accessories("Piccolo accessory", 40.00,2));
            session.save(new Accessories("Clarinet accessory", 40.00,2));
            session.save(new Accessories("Basson accessory", 50.00,2));
        }
        tx.commit();
        session.close();
        
        System.out.println("Accessories table loaded.");
    }
        
    
}
