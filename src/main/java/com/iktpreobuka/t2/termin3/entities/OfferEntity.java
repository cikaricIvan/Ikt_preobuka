package com.iktpreobuka.t2.termin3.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfferEntity {
	public enum EUofferStatus{WAIT_FOR_APPROVING, APPROVED, DECLINED,EXPIRED}
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull(message="Offer name must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String offerName; 
	@Size(min=5,max=20,message = "Offer description must be between {min} and {max} characters long.")
	@NotNull(message="Offer description must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String offerDescription;
	@JsonView(Views.Public.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	@Column(nullable=false)
	private LocalDate offerCreated;
	@Future(message="Offer expires must be in future")
	@JsonView(Views.Public.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	@Column(nullable=false)
	private LocalDate offerExpires;
	@Min(value=1)
	@NotNull(message="Regular price must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private Double regularPrice;
	@Min(value=1)
	@NotNull(message="action price must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private Double actionPrice;
	@NotNull(message="Image path must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String imagePath;
	@NotNull(message="Available offer must be specified")
	@Min(value=0)
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private Integer availableOffer;
	@NotNull(message="Bought offer must be specified")
	@Min(value=0)
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private Integer boughtOffer;
	@JsonView(Views.Public.class)
	@Column
	private EUofferStatus offerStatus;
	@Column
	@Version
	private Integer version;
	@JsonView(Views.Public.class)
	@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn(name="category")
	private CategoryEntity category;
	@JsonView(Views.Admin.class)
	@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn(name="user")
	private UserEntity user;
	@JsonView(Views.Admin.class)
	@JsonIgnore
	@OneToMany(mappedBy = "offer",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	private List<BillEntity> bill=new ArrayList<BillEntity>();
	@JsonView(Views.Admin.class)
	@JsonIgnore
	@OneToMany(mappedBy = "offer",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	private List<VoucherEntity> voucher=new ArrayList<VoucherEntity>();


	public OfferEntity() {
		super();
		// TODO Auto-generated constructor stub
		
	}

	

	public OfferEntity(Integer id, @NotNull(message = "Offer name must be specified") String offerName,
			@Size(min = 5, max = 20, message = "Offer description must be between {min} and {max} characters long.") @NotNull(message = "Offer description must be specified") String offerDescription,
			LocalDate offerCreated, @Future(message = "Offer expires must be in future") LocalDate offerExpires,
			@Min(1) @NotNull(message = "Regular price must be specified") Double regularPrice,
			@Min(1) @NotNull(message = "action price must be specified") Double actionPrice,
			@NotNull(message = "Image path must be specified") String imagePath,
			@NotNull(message = "Available offer must be specified") @Min(0) Integer availableOffer,
			@NotNull(message = "Bought offer must be specified") @Min(0) Integer boughtOffer, EUofferStatus offerStatus,
			Integer version, CategoryEntity category, UserEntity user, List<BillEntity> bill,
			List<VoucherEntity> voucher) {
		super();
		this.id = id;
		this.offerName = offerName;
		this.offerDescription = offerDescription;
		this.offerCreated = offerCreated;
		this.offerExpires = offerExpires;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.imagePath = imagePath;
		this.availableOffer = availableOffer;
		this.boughtOffer = boughtOffer;
		this.offerStatus = offerStatus;
		this.version = version;
		this.category = category;
		this.user = user;
		this.bill = bill;
		this.voucher = voucher;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferDescription() {
		return offerDescription;
	}
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
	public LocalDate getOfferCreated() {
		return offerCreated;
	}
	public void setOfferCreated(LocalDate offerCreated) {
		this.offerCreated = offerCreated;
	}
	public LocalDate getOfferExpires() {
		return offerExpires;
	}
	public void setOfferExpires(LocalDate offerExpires) {
		this.offerExpires = offerExpires;
	}
	public Double getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}
	public Double getActionPrice() {
		return actionPrice;
	}
	public void setActionPrice(Double actionPrice) {
		this.actionPrice = actionPrice;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Integer getAvailableOffer() {
		return availableOffer;
	}
	public void setAvailableOffer(Integer availableOffer) {
		this.availableOffer = availableOffer;
	}
	public Integer getBoughtOffer() {
		return boughtOffer;
	}
	public void setBoughtOffer(Integer boughtOffer) {
		this.boughtOffer = boughtOffer;
	}
	public EUofferStatus getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(EUofferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public CategoryEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}



	public List<BillEntity> getBill() {
		return bill;
	}



	public void setBill(List<BillEntity> bill) {
		this.bill = bill;
	}




	public List<VoucherEntity> getVoucher() {
		return voucher;
	}




	public void setVoucher(List<VoucherEntity> voucher) {
		this.voucher = voucher;
	}

}
