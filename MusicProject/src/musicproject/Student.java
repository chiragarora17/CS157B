
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
    @JoinColumn(name="instrument_id")
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
        
        Instrument flute = Instrument.find("Flute");
        Instrument piccolo = Instrument.find("Piccolo");
        Instrument clarinet = Instrument.find("Clarinet");
        Instrument basson = Instrument.find("Basson");
        
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
     * Fetch the student with a matching name.
     * @param name the name to match.
     * @return the student or null.
     */
    public static Student find(String name)
    {
        // Query by example.
        Student prototype = new Student();
        prototype.setName(name);
        Example example = Example.create(prototype);
        
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Student.class);
        criteria.add(example);
        
        Student stu = (Student) criteria.uniqueResult();
        
        session.close();
        return stu;
    }
    
    /**
     * Print a professor's classes.
     * @param first the professor's first name.
     * @param last the professor's last name.
     */
  /*  public static void classesOf(String first, String last)
    {
        Session session = HibernateContext.getSession();
        Criteria classCriteria = session.createCriteria(Student.class);
        Criteria teacherCriteria = classCriteria.createCriteria("teacher");

        // Match the first and last names.
        teacherCriteria.add(Restrictions.eq("firstName", first))
                       .add(Restrictions.eq("lastName", last));

        // Distinct classes sorted by subject.
        classCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        classCriteria.addOrder(Order.asc("subject"));
        List<Klass> klasses = (List<Klass>) classCriteria.list();
        
        System.out.printf("Classes taught by %s %s:\n", first, last);
        for (Klass klass : klasses) {
            System.out.printf("    %s\n", klass.getSubject());
        }
        
        session.close();
    }
    */

}

  
