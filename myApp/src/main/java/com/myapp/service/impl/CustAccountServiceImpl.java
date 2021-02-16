package com.myapp.service.impl;

import com.myapp.service.CustAccountService;
import com.myapp.domain.CustAccount;
import com.myapp.repository.CustAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CustAccount}.
 */
@Service
@Transactional
public class CustAccountServiceImpl implements CustAccountService {

    private final Logger log = LoggerFactory.getLogger(CustAccountServiceImpl.class);

    private final CustAccountRepository custAccountRepository;

    public CustAccountServiceImpl(CustAccountRepository custAccountRepository) {
        this.custAccountRepository = custAccountRepository;
    }

    @Override
    public CustAccount save(CustAccount custAccount) {
        log.debug("Request to save CustAccount : {}", custAccount);
        return custAccountRepository.save(custAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustAccount> findAll() {
        log.debug("Request to get all CustAccounts");
        return custAccountRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustAccount> findOne(Long id) {
        log.debug("Request to get CustAccount : {}", id);
        return custAccountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustAccount : {}", id);
        custAccountRepository.deleteById(id);
    }
}
