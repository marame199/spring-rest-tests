package com.test.recruitment.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * Account test
 * 
 * @author A525125
 *
 */
public class AdminTransactionTest extends AbstractTest {

	@Test
	public void createTransaction() throws Exception {
		final String request = getRequest("createOk");

		mockMvc.perform(
				post("/accounts/1/transactions/add").contentType(
						MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isCreated());
	}

	@Test
	public void createTransactionBadRequest() throws Exception {
		final String request = getRequest("createBadRequest");

		mockMvc.perform(
				post("/accounts/1/transactions/add").contentType(
						MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isBadRequest());
	}

	/**
	 * add a transaction with an existing id
	 * 
	 * @throws Exception
	 */
	@Test
	public void createTransactionExistingId() throws Exception {
		final String request = getRequest("createExistingId");
		mockMvc.perform(post("/accounts/1/transactions/add").contentType(MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isNotModified());
	}

	@Test
	public void updateTransaction() throws Exception {
		final String request = getRequest("updateOk");

		mockMvc.perform(
				put("/accounts/1/transactions/update/1").contentType(
						MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isNoContent());
	}

	@Test
	public void updateTransactionWhichNotBelongToTheAccount() throws Exception {
		final String request = getRequest("updateOk");

		mockMvc.perform(
				put("/accounts/2/transactions/update/1").contentType(
						MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isForbidden());
	}

	@Test
	public void updateUnexistingTransaction() throws Exception {
		final String request = getRequest("updateOk");

		mockMvc.perform(
				put("/accounts/1/transactions/update/8").contentType(
						MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isNotFound());
	}

	@Test
	public void updateTransactionBadRequest() throws Exception {
		final String request = "test";

		mockMvc.perform(
				put("/accounts/1/transactions/update/3").contentType(
						MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteTransaction() throws Exception {
		mockMvc.perform(delete("/accounts/1/transactions/delete/1")).andExpect(
				status().isNoContent());
	}

	@Test
	public void deleteTransactionWhichNotBelongToTheAccount() throws Exception {
		mockMvc.perform(delete("/accounts/2/transactions/delete/2")).andExpect(
				status().isForbidden());
	}

	@Test
	public void deleteUnexistingTransaction() throws Exception {
		mockMvc.perform(delete("/accounts/1/transactions/delete/99")).andExpect(
				status().isNotFound());
	}

	/**
	 * test the deletion of transaction for an unexisting account
	 *
	 * @throws Exception
	 */
	@Test
	public void deleteTransactionUnexistingAccount() throws Exception {
		mockMvc.perform(delete("/accounts/100/transactions/delete/1")).andExpect(
				status().isNotFound());
	}

	/**
	 * Get json request from test file
	 * 
	 * @param name
	 *            the filename
	 * @return the request
	 * @throws IOException
	 */
	private String getRequest(final String name) throws IOException {
		final StringWriter writer = new StringWriter();
		IOUtils.copy(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("json/" + name + ".json"), writer);
		return writer.toString();
	}

}
