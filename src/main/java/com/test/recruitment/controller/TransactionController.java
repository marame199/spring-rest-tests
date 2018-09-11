package com.test.recruitment.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.recruitment.json.TransactionResponse;

/**
 * Transaction controller
 * 
 * @author A525125
 *
 */
@RequestMapping(value = "/accounts/{accountId}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public interface TransactionController {

	/**
	 * Get transaction list by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable information
	 * @return the transaction list
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	ResponseEntity<Page<TransactionResponse>> getTransactionsByAccount(
			@PathVariable("accountId") String accountId,
			@PageableDefault Pageable p);

	/**
	 * delete the transaction identified by transactionId for the account accountId
	 *
	 * @param accountId
	 * @param transactionId
	 * @return
	 */
	@DeleteMapping("/delete/{transactionId}")
	ResponseEntity<Void> deleteTransaction(@PathVariable String accountId, @PathVariable String transactionId);

	/**
	 * add a new transaction for the account
	 * 
	 * @param accountId the account id
	 * @return
	 */
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> addTransaction(@PathVariable String accountId,
			@Valid @RequestBody TransactionResponse transaction);

	/**
	 * update a new transaction for the account
	 * 
	 * @param accountId the account id
	 * @return
	 */
	@PutMapping(value = "/update/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> updateTransaction(@PathVariable String accountId, @PathVariable String transactionId,
			@Valid @RequestBody TransactionResponse transaction);

}
