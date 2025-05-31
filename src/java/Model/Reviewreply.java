/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "REVIEWREPLY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviewreply.findAll", query = "SELECT r FROM Reviewreply r"),
    @NamedQuery(name = "Reviewreply.findById", query = "SELECT r FROM Reviewreply r WHERE r.id = :id"),
    @NamedQuery(name = "Reviewreply.findByDetails", query = "SELECT r FROM Reviewreply r WHERE r.details = :details"),
    @NamedQuery(name = "Reviewreply.findByDatereply", query = "SELECT r FROM Reviewreply r WHERE r.datereply = :datereply"),
    @NamedQuery(name = "Reviewreply.findByAccountid", query = "SELECT r FROM Reviewreply r WHERE r.accountid = :accountid"),
    @NamedQuery(name = "Reviewreply.findByReviewid", query = "SELECT r FROM Reviewreply r WHERE r.reviewid = :reviewid")})
public class Reviewreply implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "DATEREPLY")
    @Temporal(TemporalType.DATE)
    private Date datereply;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACCOUNTID")
    private int accountid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REVIEWID")
    private int reviewid;
    @Transient
    private Account account;
    
    public Reviewreply() {
    }

    public Reviewreply(Integer id) {
        this.id = id;
    }

    public Reviewreply( String details, int accountid, int reviewid) {
        this.details = details;
        this.accountid = accountid;
        this.reviewid = reviewid;
        this.datereply = new Date();
    }
    
    public Reviewreply(Integer id, String details, int accountid, int reviewid) {
        this.id = id;
        this.details = details;
        this.accountid = accountid;
        this.reviewid = reviewid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDatereply() {
        return datereply;
    }

    public void setDatereply(Date datereply) {
        this.datereply = datereply;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
        if (!(object instanceof Reviewreply)) {
            return false;
        }
        Reviewreply other = (Reviewreply) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Reviewreply[ id=" + id + " ]";
    }
    
}
