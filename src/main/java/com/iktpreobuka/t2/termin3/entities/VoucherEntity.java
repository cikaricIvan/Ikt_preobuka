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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;

@Entity
public class VoucherEntity {
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull(message="Expirition date must be specified")
	@Future(message="Expirition date must be in future")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date expirationDate;
	@NotNull(message="Is used must be specified")
	@AssertFalse
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private Boolean isUsed;
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
	

	public VoucherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VoucherEntity(Integer id,
			@NotNull(message = "Expirition date must be specified") @Future(message = "Expirition date must be in future") Date expirationDate,
			@NotNull(message = "Is used must be specified") @AssertFalse Boolean isUsed, Integer version,
			OfferEntity offer, UserEntity user) {
		super();
		this.id = id;
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
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
