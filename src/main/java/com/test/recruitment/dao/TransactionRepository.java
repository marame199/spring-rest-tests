package com.test.recruitment.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.test.recruitment.entity.Transaction;

/**
 * Transaction repository
 * 
 * @author A525125
 *
 */
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, String> {

	/**
	 * Get transactions by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable information
	 * @return
	 */
	@Query(value = "select trx from Transaction trx  where trx.accountId= ?1")
	Page<Transaction> getTransactionsByAccount(String accountId, Pageable p);

	/**
	 * get all transactions for an  account
	 *
	 * @param accountId the account id
	 * @return a list of objects of type Transaction
	 */
	@Query("select trx from Transaction trx where trx.accountId= ?1")
	List<Transaction> getTransactionList(String accountId);

}
