package com.myapp.repository;

import com.myapp.domain.CustAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustAccountRepository extends JpaRepository<CustAccount, Long> {
}
