package com.myapp.service;

import com.myapp.domain.CustAccount;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustAccount}.
 */
public interface CustAccountService {

    /**
     * Save a custAccount.
     *
     * @param custAccount the entity to save.
     * @return the persisted entity.
     */
    CustAccount save(CustAccount custAccount);

    /**
     * Get all the custAccounts.
     *
     * @return the list of entities.
     */
    List<CustAccount> findAll();


    /**
     * Get the "id" custAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustAccount> findOne(Long id);

    /**
     * Delete the "id" custAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
