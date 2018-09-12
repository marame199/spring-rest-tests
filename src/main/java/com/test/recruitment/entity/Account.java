package com.test.recruitment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Account entity
 * 
 * @author A525125
 *
 */
@Entity
@Data
public class Account implements Serializable {

	private static final long serialVersionUID = -3548441891975414771L;
	@Id
    @GeneratedValue
	private String id;

	private String number;

	private String type;

	private BigDecimal balance;

	private Date creationDate;

	private boolean isActive;
}
