package com.test.recruitment.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.recruitment.dao.TransactionRepository;
import com.test.recruitment.entity.Transaction;
import com.test.recruitment.exception.ServiceException;
import com.test.recruitment.json.ErrorCode;
import com.test.recruitment.json.TransactionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Transaction service
 * 
 * @author A525125
 *
 */
@Service
@Slf4j
public class TransactionService {

	private final AccountService accountService;

	private final TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(final AccountService accountService,
			final TransactionRepository transactionRepository) {
		this.accountService = accountService;
		this.transactionRepository = transactionRepository;
	}

	/**
     * check the existence of the account and throw an exception if it does not exists
     * @param accountId
     */
    private void checkAccountId(final String accountId) {
        if (!accountService.isAccountExist(accountId)) {
            throw new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT,
                    "Account doesn't exist");
        }
    }

    /**
     * check the existence of the transaction  and throw an exception if it does not exists
     *
     * @param transactionId
     */
    private void checkTransactionId(final String transactionId) {
        if (!isTransactionExist(transactionId)) {
            throw new ServiceException(ErrorCode.NOT_FOUND_TRANSACTION,
                    "Transaction  doesn't exist");
        }
    }

    /**
	 * Get transactions by account
	 * 
	 * @param accountId
	 *            the account id
	 * @param p
	 *            the pageable object
	 * @return
	 */
	public Page<TransactionResponse> getTransactionsByAccount(final String accountId,
                                                              final Pageable p) {
        checkAccountId(accountId);
        return new PageImpl<>(transactionRepository
                .getTransactionsByAccount(accountId, p).getContent().stream()
                .map(this::map).collect(Collectors.toList()));
    }

    /**
     * delete the transaction
     *
     * @param transactionId the transaction id
     */
    public void deleteTransaction(final String accountId, final String transactionId) throws ServiceException {
        checkAccountId(accountId);
        checkTransactionId(transactionId);
        transactionRepository.getTransactionList(accountId)
                .stream().
                filter(trx -> transactionId.equals(trx.getId())).
                findFirst().orElseThrow(() -> new ServiceException(ErrorCode.FORBIDDEN_TRANSACTION,
                "Transaction forbidden "));
        transactionRepository.deleteTransaction(transactionId);
    }




	/**
	 * Map {@link Transaction} to {@link TransactionResponse}
	 * 
	 * @param transaction
	 * @return
	 */
    private TransactionResponse map(final Transaction transaction) {
        final TransactionResponse result = new TransactionResponse();
        result.setBalance(transaction.getBalance());
        result.setId(transaction.getId());
        result.setNumber(transaction.getNumber());
        return result;
    }

    /**
     * Check if a transaction  exists
     *
     * @param transactionId the transaction id
     * @return true if the transaction exists
     */
    public boolean isTransactionExist(final String transactionId) {
        return transactionRepository.exists(transactionId);
    }
/**
 * create a transaction for the account 
 * @param accountId the accound id 
 * @param transaction the new  transaction to create 
 */
	public void addTransaction(final String accountId,final TransactionResponse transaction){
		checkAccountId(accountId);
		Transaction existingTrx=transactionRepository.findById(transaction.getId());
		if(null!=existingTrx) {
			throw new  ServiceException(ErrorCode.EXISTING_TRANSACTION,
			        "The transaction already exists ");
		}else {
			transactionRepository.addTransaction(accountId, transaction);
		}
}


}
