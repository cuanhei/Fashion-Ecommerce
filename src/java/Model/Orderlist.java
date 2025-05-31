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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@Table(name = "ORDERLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderlist.findAll", query = "SELECT o FROM Orderlist o"),
    @NamedQuery(name = "Orderlist.findById", query = "SELECT o FROM Orderlist o WHERE o.id = :id"),
    @NamedQuery(name = "Orderlist.findByOrderid", query = "SELECT o FROM Orderlist o WHERE o.orderid = :orderid"),
    @NamedQuery(name = "Orderlist.findByProductid", query = "SELECT o FROM Orderlist o WHERE o.productid = :productid"),
    @NamedQuery(name = "Orderlist.findBySizeid", query = "SELECT o FROM Orderlist o WHERE o.sizeid = :sizeid"),
    @NamedQuery(name = "Orderlist.findByQuantity", query = "SELECT o FROM Orderlist o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "Orderlist.findByReviewid", query = "SELECT o FROM Orderlist o WHERE o.reviewid = :reviewid")})
public class Orderlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ORDERID")
    private Integer orderid;
    @Column(name = "PRODUCTID")
    private Integer productid;
    @Column(name = "SIZEID")
    private Integer sizeid;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "REVIEWID")
    private Integer reviewid;
    
    @Transient
    private Product product;
    
    @Transient
    private Productsize size;

    public Orderlist() {
    }

    public Orderlist(Integer id) {
        this.id = id;
    }
    
    public Orderlist(Integer orderID, Integer productID, Integer sizeID, Integer quantity){
        this.orderid = orderID;
        this.productid = productID;
        this.sizeid = sizeID;
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Productsize getSize() {
        return size;
    }

    public void setSize(Productsize size) {
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getSizeid() {
        return sizeid;
    }

    public void setSizeid(Integer sizeid) {
        this.sizeid = sizeid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
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
        if (!(object instanceof Orderlist)) {
            return false;
        }
        Orderlist other = (Orderlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Orderlist[ id=" + id + " ]";
    }
    
}
