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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;


/**
 *
 * @author chirag
 */


@Entity
@Table(name="instrument")
public class Instrument {
    
    private int instrumentid;
    private String name;
    private double cost;
    private int inStock;
    private Description description;
    private List<Student> students;
    private List<Accessories> accessory;
    
    
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
    @OneToMany(mappedBy="instrument", targetEntity=Student.class,
    cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
    
    @ManyToMany
    @JoinTable(name="Instrument_Accessory", 
               joinColumns={@JoinColumn(name="instrument_code")},
               inverseJoinColumns={@JoinColumn(name="accessory_code")})
    public List<Accessories> getAccessory() { return accessory; }
    public void setAccessory(List<Accessories> accessory) { this.accessory = accessory; }
    
    
    /**
    * Print Instrument attributes.
    */
    public void print()
    {
        System.out.printf("%d:\t%s\t%.2f\t%d\t(%s) \n", instrumentid, name, cost,inStock, description.getdescription());
    }
    
     /**
     * Load the Instrument table.
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        Transaction tx = session.beginTransaction();
        Instrument i1=new Instrument("Clarinet", 400.00,2, new Description("This is Clarinet"));
        Instrument i2=new Instrument("Basson", 500.00,2, new Description("This is Basson"));
        Instrument i3=new Instrument("Flute ", 200.00,2, new Description("This is flute"));
        Instrument i4 = new Instrument("Piccolo", 400.00,2, new Description("This is Piccolo"));
        
        Accessories reed = Accessories.find(1);
        System.out.println(Accessories.find(1));
        i1.getAccessory().add(reed);
        i2.getAccessory().add(reed); 
        {
            session.save(i1);
            session.save(i2);
            session.save(i3);
            session.save(i4);
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
     //   criteria.addOrder(Order.asc("name"));
        
        List<Instrument> instruments = criteria.list();
        System.out.println("All Instruments:");
        System.out.println("ID:\tNAME\tCOST\tSTOCK\tDESCRIPTION_ID");
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
    public static Instrument find(String name)
    {
        // Query by example.
        Instrument prototype = new Instrument();
        prototype.setName(name);
        Example example = Example.create(prototype);
        
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Instrument.class);
        criteria.add(example);
        
        Instrument instrument = (Instrument) criteria.uniqueResult();
        
        session.close();
        return instrument;
    }
    
     public static Instrument find(int id)
    {
        // Query using HQL.
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Instrument where id = :idvar");
        
        query.setInteger("idvar", id);
        Instrument instrument = (Instrument) query.uniqueResult();
        
        session.close();
        return instrument;
    }
}
