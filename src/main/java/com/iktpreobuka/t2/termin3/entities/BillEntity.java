package com.iktpreobuka.t2.termin3.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;

@Entity
public class BillEntity {
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull(message="Payment made must be specified")
	@AssertFalse
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private Boolean paymentMade;
	@NotNull(message="Payment canceled must be specified")
	@AssertFalse
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private Boolean paymentCanceled;
	@NotNull(message="Bill created must be specified")
	@JsonView(Views.Public.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	@Column(nullable=false)
	@PastOrPresent
	private Date billCreated;
	@Version
	private Integer version;
	@JsonView(Views.Private.class)
	@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn(name="offer")
	private OfferEntity offer; 
	@JsonView(Views.Private.class)
	@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn(name="user")
	private UserEntity user;
	
	
	
	public BillEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public BillEntity(Integer id, @NotNull(message = "Payment made must be specified") @AssertFalse Boolean paymentMade,
			@NotNull(message = "Payment canceled must be specified") @AssertFalse Boolean paymentCanceled,
			@NotNull(message = "Bill created must be specified") @PastOrPresent Date billCreated, Integer version,
			OfferEntity offer, UserEntity user) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
		this.version = version;
		this.offer = offer;
		this.user = user;
	}




	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public OfferEntity getOffer() {
		return offer;
	}
	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
	
	
}
