package com.myapp.web.rest;

import com.myapp.domain.CustAccount;
import com.myapp.service.CustAccountService;
import com.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.myapp.domain.CustAccount}.
 */
@RestController
@RequestMapping("/api")
public class CustAccountResource {

    private final Logger log = LoggerFactory.getLogger(CustAccountResource.class);

    private static final String ENTITY_NAME = "custAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustAccountService custAccountService;

    public CustAccountResource(CustAccountService custAccountService) {
        this.custAccountService = custAccountService;
    }

    /**
     * {@code POST  /cust-accounts} : Create a new custAccount.
     *
     * @param custAccount the custAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custAccount, or with status {@code 400 (Bad Request)} if the custAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-accounts")
    public ResponseEntity<CustAccount> createCustAccount(@Valid @RequestBody CustAccount custAccount) throws URISyntaxException {
        log.debug("REST request to save CustAccount : {}", custAccount);
        if (custAccount.getId() != null) {
            throw new BadRequestAlertException("A new custAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustAccount result = custAccountService.save(custAccount);
        return ResponseEntity.created(new URI("/api/cust-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-accounts} : Updates an existing custAccount.
     *
     * @param custAccount the custAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custAccount,
     * or with status {@code 400 (Bad Request)} if the custAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-accounts")
    public ResponseEntity<CustAccount> updateCustAccount(@Valid @RequestBody CustAccount custAccount) throws URISyntaxException {
        log.debug("REST request to update CustAccount : {}", custAccount);
        if (custAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustAccount result = custAccountService.save(custAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cust-accounts} : get all the custAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custAccounts in body.
     */
    @GetMapping("/cust-accounts")
    public List<CustAccount> getAllCustAccounts() {
        log.debug("REST request to get all CustAccounts");
        return custAccountService.findAll();
    }

    /**
     * {@code GET  /cust-accounts/:id} : get the "id" custAccount.
     *
     * @param id the id of the custAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-accounts/{id}")
    public ResponseEntity<CustAccount> getCustAccount(@PathVariable Long id) {
        log.debug("REST request to get CustAccount : {}", id);
        Optional<CustAccount> custAccount = custAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custAccount);
    }

    /**
     * {@code DELETE  /cust-accounts/:id} : delete the "id" custAccount.
     *
     * @param id the id of the custAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-accounts/{id}")
    public ResponseEntity<Void> deleteCustAccount(@PathVariable Long id) {
        log.debug("REST request to delete CustAccount : {}", id);
        custAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
