package com.iktpreobuka.t2.termin3.dto;

import java.util.Date;



import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;


public class BillDTO {
	@NotNull(message="Payment made must be specified")
	@AssertFalse
	@JsonView(Views.Admin.class)
	private Boolean paymentMade;
	@NotNull(message="Payment canceled must be specified")
	@AssertFalse
	@JsonView(Views.Admin.class)
	private Boolean paymentCanceled;
	@PastOrPresent(message="Bill created must be in present or in past")
	@JsonView(Views.Public.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date billCreated;
	
	
	
	
	
	
	public BillDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public BillDTO(@NotNull(message = "Payment made must be specified") @AssertFalse Boolean paymentMade,
			@NotNull(message = "Payment canceled must be specified") @AssertFalse Boolean paymentCanceled,
			@PastOrPresent(message = "Bill created must be in present or in past") Date billCreated) {
		super();
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
	}







	public Boolean getPaymentMade() {
		return paymentMade;
	}
	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}
	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}
	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}
	public Date getBillCreated() {
		return billCreated;
	}
	public void setBillCreated(Date billCreated) {
		this.billCreated = billCreated;
	}

	
	
	
	
}
