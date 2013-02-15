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
    
    public Instrument()


}
