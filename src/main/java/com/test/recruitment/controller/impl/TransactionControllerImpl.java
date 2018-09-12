package com.test.recruitment.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.recruitment.controller.TransactionController;
import com.test.recruitment.json.TransactionResponse;
import com.test.recruitment.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link TransactionController}
 * 
 * @author A525125
 *
 */
@Slf4j
@RestController
public class TransactionControllerImpl implements TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionControllerImpl(final TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public ResponseEntity<Page<TransactionResponse>> getTransactionsByAccount(
			@PathVariable("accountId") final String accountId,
			@PageableDefault final Pageable p) {
		final Page<TransactionResponse> page = transactionService
				.getTransactionsByAccount(accountId, p);
		if (null == page || page.getTotalElements() == 0) {
			log.debug("Cannot find transaction for account {}", accountId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok().body(page);
	}

	@Override
	public ResponseEntity<Void> deleteTransaction(@PathVariable("accountId") final String accountId, @PathVariable("transactionId") final String transactionId) {
		transactionService.deleteTransaction(accountId, transactionId);
		log.info("The transaction {} of the account {} has been successfuly deleted ", transactionId, accountId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> addTransaction(@PathVariable("accountId") final String accountId, @Valid @RequestBody final TransactionResponse transaction) {
		transactionService.addTransaction(accountId, transaction);
		log.info("The transaction has been succesfussly created for the accound {} ", accountId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> updateTransaction(@PathVariable("accountId") final String accountId, @PathVariable final String transactionId, @Valid @RequestBody final TransactionResponse transaction) {
		transactionService.updateTransaction(accountId, transactionId, transaction);
		log.info("The transaction {}  has been succesfussly updated for the accound {} ", transactionId, accountId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
