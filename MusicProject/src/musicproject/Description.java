/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicproject;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 *
 * @author chirag
 */

@Entity
@Table(name="description")
public class Description {
    
    private int descriptionid;
    private String description;
 
    public Description() {}
    
    public Description(String description)
    {
        this.description = description;
    }

    @Id
    @GeneratedValue
    @Column(name="descriptionid")
    public int getdescId() { return descriptionid; }
    public void setdescId(int descriptionid) { this.descriptionid = descriptionid; }

    @Column(name="description")
    public String getdescription() { return description; }
    public void setdescription(String description) { this.description = description; }
    
    
    
}

    
