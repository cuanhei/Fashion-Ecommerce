/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.PrePersist;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LIM CUAN HEI
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByUsername", query = "SELECT a FROM Account a WHERE LOWER(a.username) = LOWER(:username)"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByRole", query = "SELECT a FROM Account a WHERE a.role = :role"),
    @NamedQuery(name = "Account.findByStatus", query = "SELECT a FROM Account a WHERE a.status = :status"),
    @NamedQuery(name = "Account.findByDatereg", query = "SELECT a FROM Account a WHERE a.datereg = :datereg"),
    @NamedQuery(name = "Account.findByPersonid", query = "SELECT a FROM Account a WHERE a.personid = :personid"),
    @NamedQuery(name = "Account.findByRoles", query = "SELECT a FROM Account a WHERE a.role IN :roles")
})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROLE")
    private Character role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "DATEREG")
    @Temporal(TemporalType.DATE)
    private Date datereg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERSONID")
    private int personid;
    @Size(max = 100)
    @Column(name = "IMGPATH")
    private String imgpath;
    @Transient
    private Person person;
    @Transient
    private Double totalspend;
    
    public Account() {
    }
    
    public Account(Integer id) {
        this.id = id;
    }
    
    public Account(String username, String password, Character role, Boolean status, int personid) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.personid = personid;
    }

    public Account(Integer id, String username, String password, Character role, Boolean status, int personid) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.personid = personid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getRole() {
        return role;
    }

    public void setRole(Character role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDatereg() {
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        String formattedDate = sdf.format(datereg);
        return formattedDate;
    }

    public void setDatereg(Date datereg) {
        this.datereg = datereg;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }
    
    public Person getPerson(){
        return person;
    }
    
    public Double getTotalSpend(){
        return totalspend;
    }
    
    public void setTotalSpend(Double totalspend){
        this.totalspend = totalspend;
    }
    
    public static Account getLoggedAccount(HttpServletRequest request, HttpServletResponse response){
        Account loggedAcc = null;
        try{
            HttpSession session = request.getSession(false); // returns null if no session exists
            if(session == null)
            {response.sendRedirect("../user/Home");}
            loggedAcc = (Account)session.getAttribute("loggedAcc");
            if(loggedAcc == null)
            {response.sendRedirect("../user/Home");}
        }catch(Exception ex){
        
        }
        return loggedAcc;
    }
    
    public String getRoleStr(){
        switch(role){
            case 'm': return "Member";
            case 'V': return "VIP";
            case 'S': return "Staff";
            case 'M': return "Manager";
            default:
                return "Member";
        }
    }
    
    public String getRoleTagColor(){
        switch(role){
            case 'm': return "dark";//Member
            case 'V': return "primary";//VIP
            case 'S': return "warning";//Staff
            case 'M': return "danger";//Manager
            default:
                return "dark";
        }
    }
    
    public boolean isBanned(){
        return !status;
    }
    public boolean isAdmin(){
        return role == 'M' || role == 'S';
    }
    
    public boolean isManager(){
        return role=='M';
    }
    
    public boolean isVIP(){
        return role == 'V';
    }

    public static boolean isUsernameExist(EntityManager em, String username) {

        // JPQL query to check if the username already exists
        String jpql = "SELECT COUNT(a) FROM Account a WHERE LOWER(a.username) = LOWER(:username)";

        Query query = em.createQuery(jpql);
        query.setParameter("username", username);

        // Get the count of accounts with the same username
        Long count = (Long) query.getSingleResult();

        return count > 0; // If count > 0, the username exists
    }
    
    @PrePersist
    public void prePersist() {
        if (this.datereg == null) {
            this.datereg = new Date();  // Set current date if it's not already set
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", status=" + status + ", datereg=" + datereg + ", personid=" + personid + ", imgpath=" + imgpath + '}';
    }
    
}
