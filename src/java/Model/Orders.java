/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.transaction.UserTransaction;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByAddress", query = "SELECT o FROM Orders o WHERE o.address = :address"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByAccountid", query = "SELECT o FROM Orders o WHERE o.accountid = :accountid"),
    @NamedQuery(name = "Orders.findByPaymentid", query = "SELECT o FROM Orders o WHERE o.paymentid = :paymentid"),
    @NamedQuery(name = "Orders.findByOrderdate", query = "SELECT o FROM Orders o WHERE o.orderdate = :orderdate")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 255)
    private String address;
    @Size(max = 20)
    private String status;
    private Integer accountid;
    private Integer paymentid;
    @Temporal(TemporalType.DATE)
    private Date orderdate;
    
    @Transient
    private List<Orderlist> orderLists;
    @Transient
    private Payment payment;
    @Transient
    private Account customer;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }
    
    public Orders(String address, String status, Integer accountID, Integer paymentID){
        this.address = address;
        this.status = status;
        this.accountid = accountID;
        this.paymentid = paymentID;
        this.orderdate = new Date(); // Set current date
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public List<Orderlist> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(List<Orderlist> orderLists) {
        this.orderLists = orderLists;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }
    
    public String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        String formattedDate = sdf.format(orderdate);
        return formattedDate;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }
    
    
    
    @PrePersist
    public void prePersist() {
        if (this.orderdate == null) {
            this.orderdate = new Date();  // Set current date if it's not already set
        }
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Orders[ id=" + id + " ]";
    }
    
}
