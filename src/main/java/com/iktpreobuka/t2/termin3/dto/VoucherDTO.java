package com.iktpreobuka.t2.termin3.dto;

import java.util.Date;


import javax.persistence.Column;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;


public class VoucherDTO {
	
	@NotNull(message="Expirition date must be specified")
	@Future(message="Expirition date must be in future")
	@JsonView(Views.Public.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date expirationDate;
	@NotNull(message="Is used must be specified")
	@AssertFalse
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private Boolean isUsed;

	public VoucherDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public VoucherDTO(
			@NotNull(message = "Expirition date must be specified") @Future(message = "Expirition date must be in future") Date expirationDate,
			@NotNull(message = "Is used must be specified") @AssertFalse Boolean isUsed) {
		super();
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
	}


	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}




}
