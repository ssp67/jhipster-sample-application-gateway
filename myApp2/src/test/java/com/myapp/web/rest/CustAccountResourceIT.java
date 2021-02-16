package com.myapp.web.rest;

import com.myapp.MyApp2App;
import com.myapp.domain.CustAccount;
import com.myapp.repository.CustAccountRepository;
import com.myapp.service.CustAccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustAccountResource} REST controller.
 */
@SpringBootTest(classes = MyApp2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustAccountResourceIT {

    private static final String DEFAULT_DEPARTMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    @Autowired
    private CustAccountRepository custAccountRepository;

    @Autowired
    private CustAccountService custAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustAccountMockMvc;

    private CustAccount custAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustAccount createEntity(EntityManager em) {
        CustAccount custAccount = new CustAccount()
            .departmentName(DEFAULT_DEPARTMENT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountType(DEFAULT_ACCOUNT_TYPE);
        return custAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustAccount createUpdatedEntity(EntityManager em) {
        CustAccount custAccount = new CustAccount()
            .departmentName(UPDATED_DEPARTMENT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE);
        return custAccount;
    }

    @BeforeEach
    public void initTest() {
        custAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustAccount() throws Exception {
        int databaseSizeBeforeCreate = custAccountRepository.findAll().size();
        // Create the CustAccount
        restCustAccountMockMvc.perform(post("/api/cust-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custAccount)))
            .andExpect(status().isCreated());

        // Validate the CustAccount in the database
        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CustAccount testCustAccount = custAccountList.get(custAccountList.size() - 1);
        assertThat(testCustAccount.getDepartmentName()).isEqualTo(DEFAULT_DEPARTMENT_NAME);
        assertThat(testCustAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCustAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void createCustAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custAccountRepository.findAll().size();

        // Create the CustAccount with an existing ID
        custAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustAccountMockMvc.perform(post("/api/cust-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custAccount)))
            .andExpect(status().isBadRequest());

        // Validate the CustAccount in the database
        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDepartmentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custAccountRepository.findAll().size();
        // set the field null
        custAccount.setDepartmentName(null);

        // Create the CustAccount, which fails.


        restCustAccountMockMvc.perform(post("/api/cust-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custAccount)))
            .andExpect(status().isBadRequest());

        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custAccountRepository.findAll().size();
        // set the field null
        custAccount.setAccountNumber(null);

        // Create the CustAccount, which fails.


        restCustAccountMockMvc.perform(post("/api/cust-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custAccount)))
            .andExpect(status().isBadRequest());

        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustAccounts() throws Exception {
        // Initialize the database
        custAccountRepository.saveAndFlush(custAccount);

        // Get all the custAccountList
        restCustAccountMockMvc.perform(get("/api/cust-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].departmentName").value(hasItem(DEFAULT_DEPARTMENT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getCustAccount() throws Exception {
        // Initialize the database
        custAccountRepository.saveAndFlush(custAccount);

        // Get the custAccount
        restCustAccountMockMvc.perform(get("/api/cust-accounts/{id}", custAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custAccount.getId().intValue()))
            .andExpect(jsonPath("$.departmentName").value(DEFAULT_DEPARTMENT_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingCustAccount() throws Exception {
        // Get the custAccount
        restCustAccountMockMvc.perform(get("/api/cust-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustAccount() throws Exception {
        // Initialize the database
        custAccountService.save(custAccount);

        int databaseSizeBeforeUpdate = custAccountRepository.findAll().size();

        // Update the custAccount
        CustAccount updatedCustAccount = custAccountRepository.findById(custAccount.getId()).get();
        // Disconnect from session so that the updates on updatedCustAccount are not directly saved in db
        em.detach(updatedCustAccount);
        updatedCustAccount
            .departmentName(UPDATED_DEPARTMENT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE);

        restCustAccountMockMvc.perform(put("/api/cust-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustAccount)))
            .andExpect(status().isOk());

        // Validate the CustAccount in the database
        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeUpdate);
        CustAccount testCustAccount = custAccountList.get(custAccountList.size() - 1);
        assertThat(testCustAccount.getDepartmentName()).isEqualTo(UPDATED_DEPARTMENT_NAME);
        assertThat(testCustAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCustAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustAccount() throws Exception {
        int databaseSizeBeforeUpdate = custAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustAccountMockMvc.perform(put("/api/cust-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(custAccount)))
            .andExpect(status().isBadRequest());

        // Validate the CustAccount in the database
        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustAccount() throws Exception {
        // Initialize the database
        custAccountService.save(custAccount);

        int databaseSizeBeforeDelete = custAccountRepository.findAll().size();

        // Delete the custAccount
        restCustAccountMockMvc.perform(delete("/api/cust-accounts/{id}", custAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustAccount> custAccountList = custAccountRepository.findAll();
        assertThat(custAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
