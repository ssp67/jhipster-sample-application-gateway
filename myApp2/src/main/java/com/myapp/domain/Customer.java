package com.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fname", nullable = false)
    private String fname;

    @NotNull
    @Column(name = "lname", nullable = false)
    private String lname;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CustAccount> custAccounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public Customer fname(String fname) {
        this.fname = fname;
        return this;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public Customer lname(String lname) {
        this.lname = lname;
        return this;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Set<CustAccount> getCustAccounts() {
        return custAccounts;
    }

    public Customer custAccounts(Set<CustAccount> custAccounts) {
        this.custAccounts = custAccounts;
        return this;
    }

    public Customer addCustAccount(CustAccount custAccount) {
        this.custAccounts.add(custAccount);
        custAccount.setCustomer(this);
        return this;
    }

    public Customer removeCustAccount(CustAccount custAccount) {
        this.custAccounts.remove(custAccount);
        custAccount.setCustomer(null);
        return this;
    }

    public void setCustAccounts(Set<CustAccount> custAccounts) {
        this.custAccounts = custAccounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", fname='" + getFname() + "'" +
            ", lname='" + getLname() + "'" +
            "}";
    }
}
