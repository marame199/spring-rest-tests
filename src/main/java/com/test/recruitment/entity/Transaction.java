package com.test.recruitment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * Transaction entity
 * 
 * @author A525125
 *
 */
@Data
@Entity
public class Transaction implements Serializable {

	private static final long serialVersionUID = 706690724306325415L;
	@Id
	private String id;

	private String accountId;

	private String number;

	private BigDecimal balance;
}
