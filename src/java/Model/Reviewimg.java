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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@Table(name = "REVIEWIMG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reviewimg.findAll", query = "SELECT r FROM Reviewimg r"),
    @NamedQuery(name = "Reviewimg.findById", query = "SELECT r FROM Reviewimg r WHERE r.id = :id"),
    @NamedQuery(name = "Reviewimg.findByReviewid", query = "SELECT r FROM Reviewimg r WHERE r.reviewid = :reviewid"),
    @NamedQuery(name = "Reviewimg.findByImgpath", query = "SELECT r FROM Reviewimg r WHERE r.imgpath = :imgpath")})
public class Reviewimg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REVIEWID")
    private int reviewid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "IMGPATH")
    private String imgpath;

    public Reviewimg() {
    }

    public Reviewimg(Integer id) {
        this.id = id;
    }

    public Reviewimg(int reviewid, String imgpath) {
        this.reviewid = reviewid;
        this.imgpath = imgpath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
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
        if (!(object instanceof Reviewimg)) {
            return false;
        }
        Reviewimg other = (Reviewimg) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Reviewimg[ id=" + id + " ]";
    }
    
}
