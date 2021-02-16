package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CustAccount.
 */
@Entity
@Table(name = "cust_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @OneToMany(mappedBy = "custAccount")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "custAccounts", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public CustAccount departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public CustAccount accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public CustAccount accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public CustAccount transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public CustAccount addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setCustAccount(this);
        return this;
    }

    public CustAccount removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setCustAccount(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustAccount customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustAccount)) {
            return false;
        }
        return id != null && id.equals(((CustAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustAccount{" +
            "id=" + getId() +
            ", departmentName='" + getDepartmentName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
