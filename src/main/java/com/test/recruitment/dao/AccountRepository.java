package com.test.recruitment.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.test.recruitment.entity.Account;

/**
 * Account repository
 * 
 * @author A525125
 *
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
}
