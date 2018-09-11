package com.test.recruitment.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.test.recruitment.dao.TransactionRepository;
import com.test.recruitment.entity.Transaction;
import com.test.recruitment.json.TransactionResponse;

/**
 * Implementation of {@link TransactionRepository}
 * 
 * @author A525125
 *
 */
@Repository
public class TransactionRepositoryImpl implements TransactionRepository,
InitializingBean {

	private List<Transaction> transactions;

	@Override
	public void afterPropertiesSet() {
		transactions = new ArrayList<>();
		{
			final Transaction transaction = new Transaction();
			transaction.setAccountId("1");
			transaction.setBalance(BigDecimal.valueOf(42.12));
			transaction.setId("1");
			transaction.setNumber("12151885120");
			transactions.add(transaction);
		}
		{
			final Transaction transaction = new Transaction();
			transaction.setAccountId("1");
			transaction.setBalance(BigDecimal.valueOf(456.00));
			transaction.setId("2");
			transaction.setNumber("12151885121");
			transactions.add(transaction);
		}
		{
			final Transaction transaction = new Transaction();
			transaction.setAccountId("1");
			transaction.setBalance(BigDecimal.valueOf(-12.12));
			transaction.setId("3");
			transaction.setNumber("12151885122");
			transactions.add(transaction);
		}
	}

	@Override
	public Transaction findById(final String id) {
		return transactions.stream()
				.filter(transaction -> transaction.getId().equals(id))
				.findFirst().orElse(null);
	}

	@Override
	public Page<Transaction> getTransactionsByAccount(final String accountId,
			final Pageable p) {
		return new PageImpl<>(getTransactionList(accountId));
	}

	@Override
	public List<Transaction> getTransactionList(final String accountId) {
		return transactions.stream()
				.filter(t -> t.getAccountId().equals(accountId))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteTransaction(final String transactionId) {
		transactions.remove(findById(transactionId));
	}


	@Override
	public boolean exists(final String transactionId) {
		return transactions.stream().anyMatch(trx -> trx.getId().equals(transactionId));
	}

	@Override
	public void addTransaction(final String accountId, final TransactionResponse transaction) {
		final Transaction trx = new Transaction();
		trx.setAccountId(accountId);
		trx.setBalance(transaction.getBalance());
		trx.setNumber(transaction.getNumber());
		trx.setId(transaction.getId());
		transactions.add(trx);
	}
}
