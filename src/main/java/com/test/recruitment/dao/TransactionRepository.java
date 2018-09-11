package com.test.recruitment.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.recruitment.entity.Transaction;
import com.test.recruitment.json.TransactionResponse;

/**
 * Transaction repository
 * 
 * @author A525125
 *
 */
public interface TransactionRepository {


	/**
	 * Get transaction by Id
	 *
	 * @param id
	 *            id of the transaction to get
	 * @return the transaction corresponding to the given id or null
	 */
	Transaction findById(String id);

	/**
	 * Get transactions by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable information
	 * @return
	 */
	Page<Transaction> getTransactionsByAccount(String accountId, Pageable p);

	/**
	 * get all transactions for an  account
	 *
	 * @param accountId the account id
	 * @return a list of objects of type Transaction
	 */
	List<Transaction> getTransactionList(String accountId);

	/**
	 * delete the transaction
	 *
	 * @param transactionId the transaction id
	 */
	void deleteTransaction(String transactionId);

	/**
	 * check if a transaction exists
	 *
	 * @param transactionId the transaction id
	 * @return true if the  transaction exist
	 */
	boolean exists(String transactionId);

	/**
	 * create a new transaction for the account
	 * 
	 * @param accountId   the account id
	 * @param transaction the transaction associated to the account
	 */
	void addTransaction(String accountId, TransactionResponse transaction);
}
