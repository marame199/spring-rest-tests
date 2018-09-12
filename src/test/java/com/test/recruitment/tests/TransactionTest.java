package com.test.recruitment.tests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

/**
 * Account test
 * 
 * @author A525125
 *
 */
public class TransactionTest extends AbstractTest {

	@Test
	public void getTransactions() throws Exception {
		mockMvc.perform(get("/accounts/1/transactions"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalElements", is(2)))
		.andExpect(jsonPath("$.content[0].number", is("0000013")))
		.andExpect(jsonPath("$.content[0].balance", is(13.0)));
	}

	/**
	 * Filter results by size
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTransactionsSizeFilter() throws Exception {
		mockMvc.perform(get("/accounts/2/transactions?size=1")).andExpect(status().isOk())
		.andExpect(jsonPath("$.totalElements", is(1)));
	}

	/**
	 * Filter result by id asc
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTransactionsSortById() throws Exception {
		mockMvc.perform(get("/accounts/2/transactions?sort=id,asc")).andExpect(status().isOk())
		.andExpect(jsonPath("$.totalElements", is(2))).andExpect(jsonPath("$.content[0].id", is("3")))
		.andExpect(jsonPath("$.content[1].id", is("4")));
	}

	/**
	 * Filter result by id asc
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTransactionsFilterBySizeAndPage1() throws Exception {
		mockMvc.perform(get("/accounts/2/transactions?size=1&page=1")).andExpect(status().isOk())
		.andExpect(jsonPath("$.totalElements", is(1))).andExpect(jsonPath("$.content[0].id", is("4")));
	}

	/**
	 * Filter result by id asc
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTransactionsFilterBySizeAndPage2() throws Exception {
		mockMvc.perform(get("/accounts/2/transactions?size=1&page=0")).andExpect(status().isOk())
		.andExpect(jsonPath("$.totalElements", is(1))).andExpect(jsonPath("$.content[0].id", is("3")));
	}

	@Test
	public void getTransactionsNoContent() throws Exception {
		mockMvc.perform(get("/accounts/20/transactions")).andExpect(
				status().isNoContent());
	}

	@Test
	public void getTransactionsOnUnexistingAccount() throws Exception {
		mockMvc.perform(get("/accounts/3/transactions"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.errorCode", is("NOT_FOUND_ACCOUNT")));
	}
}
