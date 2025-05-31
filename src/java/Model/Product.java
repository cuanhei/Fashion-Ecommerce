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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByIds", query = "SELECT p FROM Product p WHERE p.id IN :id"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findLikeName", query = "SELECT a FROM Product a WHERE LOWER(a.name) LIKE LOWER(:name)"),
    @NamedQuery(name = "Product.findBySellingprice", query = "SELECT p FROM Product p WHERE p.sellingprice = :sellingprice"),
    @NamedQuery(name = "Product.findBySupplierprice", query = "SELECT p FROM Product p WHERE p.supplierprice = :supplierprice"),
    @NamedQuery(name = "Product.findByImgpath", query = "SELECT p FROM Product p WHERE p.imgpath = :imgpath"),
    @NamedQuery(name = "Product.findByDaterelease", query = "SELECT p FROM Product p WHERE p.daterelease = :daterelease"),
    @NamedQuery(name = "Product.deleteById", query = "DELETE FROM Product p WHERE p.id= :id")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "NAME")
    private String name;
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SELLINGPRICE")
    private double sellingprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUPPLIERPRICE")
    private double supplierprice;
    @Size(max = 200)
    @Column(name = "IMGPATH")
    private String imgpath;
    @Column(name = "DATERELEASE")
    @Temporal(TemporalType.DATE)
    private Date daterelease;
    @Transient
    private List<Category> categories;
    @Transient
    private Integer totalQty;
    @Transient
    private Long totalSold;

    public Long getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Long totalSold) {
        this.totalSold = totalSold;
    }
    @Transient
    private Double rate;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, double sellingprice, double supplierprice) {
        this.id = id;
        this.sellingprice = sellingprice;
        this.supplierprice = supplierprice;
    }
    
    public Product(String name, String description, double sellingprice, double supplierprice) {
        this.name = name;
        this.description = description;
        this.sellingprice = sellingprice;
        this.supplierprice = supplierprice;
        this.daterelease = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(double sellingprice) {
        this.sellingprice = sellingprice;
    }

    public double getSupplierprice() {
        return supplierprice;
    }

    public void setSupplierprice(double supplierprice) {
        this.supplierprice = supplierprice;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Date getDaterelease() {
        return daterelease;
    }

    public void setDaterelease(Date daterelease) {
        this.daterelease = daterelease;
    }
    
    public String getDateRelease() {
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        String formattedDate = sdf.format(daterelease);
        return formattedDate;
    }
    
    public void setCategory(List<Category> categories){
        this.categories = categories;
    }
    
    public List<Category> getCategories(){
        return categories;
    }
    
    public void setTotalQty(Integer totalQty){
        this.totalQty = totalQty;
    }
    
    public Integer getTotalQty(){
        return this.totalQty;
    }
    
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Product[ id=" + id + " ]";
    }
    
}
