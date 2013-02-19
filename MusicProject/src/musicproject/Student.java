
package musicproject;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author chirag
 */
@Entity
@Table(name="student")
public class Student {
    
    private int studentid;
    private String name;
    private Instrument instrument_id;

    public Student() {}
    
    public Student(String name)
    {
        this.name= name;
    }
    @Id
    @GeneratedValue
    @Column(name="studentid")
    public int getStudentid() { return studentid; }
    public void setStudentid(int studentid) { this.studentid = studentid; }
       
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @ManyToOne
    @JoinColumn(name="instrumentid")
    public Instrument getInstrument() { return instrument_id; }
    public void setInstrument(Instrument instrument_id) { this.instrument_id = instrument_id; }
 
    
    /**
     * Print student attributes.
     */
    private void print()
    {
        System.out.printf("%d: %s\n", studentid, name);
    }
    
     /**
     * Load the Student table.
     */
    public static void load()
    {
        Session session = HibernateContext.getSession();
        
        Instrument flute = Instrument.find(1);
        Instrument piccolo = Instrument.find(2);
        Instrument clarinet = Instrument.find(3);
        Instrument basson = Instrument.find(4);
        System.out.println(flute);
        Student s1 = new Student("Ron Mak");
        s1.setInstrument(flute);
        Student s2 = new Student("Charles Flood");
        s1.setInstrument(clarinet);
        Student s3 = new Student("chirag arora");
        s1.setInstrument(piccolo);
        Student s4 = new Student("halbert");
        s1.setInstrument(basson);
        Student s5 = new Student("Obama");
        s1.setInstrument(flute);
       
        Transaction tx = session.beginTransaction();
        {
            session.save(s1);
            session.save(s2);
            session.save(s3);
            session.save(s4);
            session.save(s5);
        }
       
        tx.commit();
        session.close();
        
        System.out.println("Student table loaded.");
    }
    
    /**
     * List all the Students.
     */
    public static void list()
    {
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Student.class);
        criteria.addOrder(Order.asc("studentid"));
        List<Student> students = criteria.list();
               
        System.out.println("All Students:");
        
        for (Student st : students) {
            st.print();
        }
        
        session.close();
    }
    
    /**
     * Fetch the student with a matching id.
     * @param id the id to match.
     * @return the student or null.
     */
    public static Student find(int id)
    {
        // Query using HQL.
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Student where id = :idvar");
        
        query.setInteger("idvar", id);
        Student student = (Student) query.uniqueResult();
        
        session.close();
        return student;
    }

    
    /**
     * Print a professor's classes.
     * @param first the professor's first name.
     * @param last the professor's last name.
     */
    public static void instrumentplayed(String name)
    {
        Session session = HibernateContext.getSession();
        Criteria studentCriteria = session.createCriteria(Student.class);
        Criteria instrumentCriteria = studentCriteria.createCriteria("instrument");

        // Match the first and last names.
        instrumentCriteria.add(Restrictions.eq("name", name));

        // Distinct classes sorted by subject.
        studentCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //studentCriteria.addOrder(Order.asc("name"));
        List<Student> klasses = (List<Student>) studentCriteria.list();
        
        System.out.printf("Instruments %s played by :\n", name);
        for (Student klass : klasses) {
            System.out.printf("    %s\n", klass.getName());
        }
        
        session.close();
    }
    

}

  
