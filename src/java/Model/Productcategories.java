/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@Table(name = "PRODUCTCATEGORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productcategories.findAll", query = "SELECT p FROM Productcategories p"),
    @NamedQuery(name = "Productcategories.findById", query = "SELECT p FROM Productcategories p WHERE p.id = :id"),
    @NamedQuery(name = "Productcategories.findByProductid", query = "SELECT p FROM Productcategories p WHERE p.productid = :productid"),
    @NamedQuery(name = "Productcategories.findByCategoryid", query = "SELECT p.productid FROM Productcategories p WHERE p.categoryid = :categoryid"),
    @NamedQuery(name = "Productcategories.deleteByProductid", query = "DELETE FROM Productcategories pc WHERE pc.productid = :productid"),
})
public class Productcategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PRODUCTID")
    private Integer productid;
    @Column(name = "CATEGORYID")
    private Integer categoryid;

    public Productcategories() {
    }

    public Productcategories(Integer id) {
        this.id = id;
    }
    
    public Productcategories(Integer productID, Integer categoryID) {
        this.productid = productID;
        this.categoryid = categoryID;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }
    
    public static List<Productcategories> getByProductID(EntityManager em, Integer productID){
        
        Query productCategoriesQuery = em.createNamedQuery("Productcategories.findByProductid");
        productCategoriesQuery.setParameter("productid", productID);
        List<Productcategories> prodCategories = (List<Productcategories>)productCategoriesQuery.getResultList();
        return prodCategories;
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
        if (!(object instanceof Productcategories)) {
            return false;
        }
        Productcategories other = (Productcategories) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Productcategories[ id=" + id + " ]";
    }
    
}
