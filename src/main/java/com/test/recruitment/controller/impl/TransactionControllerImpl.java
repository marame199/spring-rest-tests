package com.test.recruitment.controller.impl;

import lombok.extern.slf4j.Slf4j;

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
import com.test.recruitment.entity.Transaction;
import com.test.recruitment.json.TransactionResponse;
import com.test.recruitment.service.TransactionService;

/**
 * Implementation of {@link TransactionController}
 * 
 * @author A525125
 *
 */
@Slf4j
@RestController
public class TransactionControllerImpl implements TransactionController {

	private TransactionService transactionService;

	@Autowired
	public TransactionControllerImpl(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public ResponseEntity<Page<TransactionResponse>> getTransactionsByAccount(
			@PathVariable("accountId") String accountId,
			@PageableDefault Pageable p) {
		Page<TransactionResponse> page = transactionService
				.getTransactionsByAccount(accountId, p);
		if (null == page || page.getTotalElements() == 0) {
			log.debug("Cannot find transaction for account {}", accountId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok().body(page);
	}

	@Override
	public ResponseEntity<Void> deleteTransaction(@PathVariable("accountId") String accountId, @PathVariable("transactionId") String transactionId) {
		transactionService.deleteTransaction(accountId, transactionId);
		log.info("The transaction {} of the account {} has been successfuly deleted ", transactionId, accountId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> addTransaction(@PathVariable("accountId") String accountId, @Valid @RequestBody TransactionResponse transaction) {
		transactionService.addTransaction(accountId, transaction);
		log.info("The transaction has been succesfussly created for the accound {} ", accountId);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	

}
