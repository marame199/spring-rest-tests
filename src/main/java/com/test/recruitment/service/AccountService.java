package com.test.recruitment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.recruitment.dao.AccountRepository;
import com.test.recruitment.entity.Account;
import com.test.recruitment.json.AccountDetailsResponse;
import com.test.recruitment.json.AccountResponse;
import com.test.recruitment.json.ErrorCode;
import com.test.recruitment.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Account service
 * 
 * @author A525125
 *
 */
@Slf4j
@Service
public class AccountService {

	private AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * Get account by user
	 * 
	 * @param p
	 *            the pageable information
	 * @return the account list
	 */
	public Page<AccountResponse> getAccounts(Pageable p) {
		List<Account> list= (List<Account>) accountRepository.findAll();
		return new PageImpl<AccountResponse>(accountRepository.findAll(p)
				.getContent().stream().map(this::mapToAccountResponse)
				.collect(Collectors.toList()));
	}

	/**
	 * Check if an account exists
	 * 
	 * @param accountId
	 *            the account id
	 * @return true if the account exists
	 */
	public boolean isAccountExist(String accountId) {
		return accountRepository.exists(accountId);
	}

	/**
	 * Get account details
	 * 
	 * @param accountId
	 *            the account id
	 * @return
	 */
	public AccountDetailsResponse getAccountDetails(String accountId) {
		log.debug("Find account {}", accountId);
		Account account = accountRepository.findOne(accountId);
		if(null==account) {
			throw new ServiceException(ErrorCode.NOT_FOUND_ACCOUNT,
					"Account doesn't exist");
		}
		return mapToAccountDetailsResponse(account);
	}

	/**
	 * Map {@link Account} to {@link AccountResponse}
	 * 
	 * @param account
	 *            the entity
	 * @return the response
	 */
	private AccountResponse mapToAccountResponse(Account account) {
		AccountResponse result = new AccountResponse();
		result.setBalance(account.getBalance());
		result.setId(account.getId());
		result.setNumber(account.getNumber());
		result.setType(account.getType());
		return result;
	}

	/**
	 * Map {@link Account} to {@link AccountDetailsResponse}
	 * 
	 * @param account
	 *            the entity
	 * @return the response
	 */
	private AccountDetailsResponse mapToAccountDetailsResponse(Account account) {
		AccountDetailsResponse result = new AccountDetailsResponse();
		result.setActive(account.isActive());
		result.setCreationDate(account.getCreationDate());
		result.setBalance(account.getBalance());
		result.setId(account.getId());
		result.setNumber(account.getNumber());
		result.setType(account.getType());
		return result;
	}

}
