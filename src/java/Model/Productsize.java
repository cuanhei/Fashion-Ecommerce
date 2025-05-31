/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@Table(name = "PRODUCTSIZE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productsize.findAll", query = "SELECT p FROM Productsize p"),
    @NamedQuery(name = "Productsize.findById", query = "SELECT p FROM Productsize p WHERE p.id = :id"),
    @NamedQuery(name = "Productsize.findByText", query = "SELECT p FROM Productsize p WHERE p.text = :text"),
    @NamedQuery(name = "Productsize.findByDescription", query = "SELECT p FROM Productsize p WHERE p.description = :description")})
public class Productsize implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 5)
    @Column(name = "TEXT")
    private String text;
    @Size(max = 100)
    @Column(name = "DESCRIPTION")
    private String description;

    public Productsize() {
    }

    public Productsize(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productsize)) {
            return false;
        }
        Productsize other = (Productsize) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Productsize[ id=" + id + " ]";
    }
    
}
