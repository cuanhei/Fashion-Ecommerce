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
import javax.persistence.Table;
import javax.transaction.NotSupportedException;
import javax.transaction.UserTransaction;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@Table(name = "CART")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c"),
    @NamedQuery(name = "Cart.findById", query = "SELECT c FROM Cart c WHERE c.id = :id"),
    @NamedQuery(name = "Cart.findByAccountid", query = "SELECT c FROM Cart c WHERE c.accountid = :accountid"),
    @NamedQuery(name = "Cart.findByProductid", query = "SELECT c FROM Cart c WHERE c.productid = :productid"),
    @NamedQuery(name = "Cart.findBySizeid", query = "SELECT c FROM Cart c WHERE c.sizeid = :sizeid"),
    @NamedQuery(name = "Cart.findByQuantity", query = "SELECT c FROM Cart c WHERE c.quantity = :quantity"),
    @NamedQuery(name = "Cart.findInCartRow", query = "SELECT c FROM Cart c WHERE c.accountid = :accountid AND c.productid = :productid AND c.sizeid =:sizeid"),
    @NamedQuery(name = "Cart.deleteByAccountid", query = "DELETE FROM Cart c WHERE c.accountid = :accountid")
})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ACCOUNTID")
    private Integer accountid;
    @Column(name = "PRODUCTID")
    private Integer productid;
    @Column(name = "SIZEID")
    private Integer sizeid;
    @Column(name = "QUANTITY")
    private Integer quantity;

    public Cart() {
    }

    public Cart(Integer id) {
        this.id = id;
    }
    
    public Cart(Integer accountID, Integer productID, Integer sizeID, Integer quantity){
        this.accountid = accountID;
        this.productid = productID;
        this.sizeid = sizeID;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
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
    
    public static void clearUserCart(EntityManager em, UserTransaction utx, Account acc){
        try{
            utx.begin();
            em.createNamedQuery("Cart.deleteByAccountid").setParameter("accountid", acc.getId()).executeUpdate();
            utx.commit();
        }catch(Exception ex){
        
        }
    }
    
    public static void removeCarts(EntityManager em, UserTransaction utx, List<Cart> carts) throws Exception{
        if(carts.isEmpty()){return;}
        
        for(Cart cart : carts){
            utx.begin();
            cart = em.merge(cart);
            em.remove(cart);
            utx.commit();
        }
    }

    public static void addToCart(EntityManager em, UserTransaction utx, Integer accountID, Integer productID, Integer sizeID, Integer quantity) throws Exception{
        
        List<Cart> carts = em.createNamedQuery("Cart.findInCartRow")
                            .setParameter("accountid", accountID)
                            .setParameter("productid", productID)
                            .setParameter("sizeid",sizeID)
                            .getResultList();
        
        if(carts.isEmpty()){
            //Add new item to cart
            Cart cart = new Cart(accountID, productID, sizeID, quantity);
            utx.begin();
            em.persist(cart);
            utx.commit();
        }else{
            //Already have item in cart
            Cart cart = carts.get(0);
            cart.setQuantity(quantity + cart.getQuantity());
            utx.begin();
            em.merge(cart);
            utx.commit();
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
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Cart[ id=" + id + " ]";
    }
    
}
