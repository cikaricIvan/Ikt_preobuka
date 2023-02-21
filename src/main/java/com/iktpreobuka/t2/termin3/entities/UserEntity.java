package com.iktpreobuka.t2.termin3.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {
	public enum EUserRole {ROLE_CUSTOMER,ROLE_ADMIN,ROLE_SELLER}
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue
	private Integer id;
    @NotNull(message="First name must be specified")
	@JsonView(Views.Private.class)
	@Column(nullable=false)
	private String firstName;
    @NotNull(message="Last name must be specified")
	@JsonView(Views.Private.class)
	@Column(nullable=false)
	private String lastName;
    @Size(min=5,max=20,message = "Name must be between {min} and {max} characters long.")
    @NotNull(message="User name must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String userName;
    @Pattern(regexp =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,100}$",message = "Password must be at least 5 characters long and contain a lowercase, an upercase letter and a number")
    @Size(min=5,message = "Password must be minimum {min} characters long.")
    @NotNull(message="Password must be specified")
	@JsonIgnore
	@Column(nullable=false)
	private String password;
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",message="Email is not valid.")
    @NotNull(message="Email must be specified")
	@JsonView(Views.Private.class)
	@Column(nullable=false)
	private String email;
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private EUserRole userRole;
	@Column
	@Version
	private Integer version;
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	private List<OfferEntity> offers=new ArrayList<OfferEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	private List<BillEntity> bills=new ArrayList<BillEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	private List<VoucherEntity> voucher=new ArrayList<VoucherEntity>();
	
	
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserEntity(Integer id, @NotNull(message = "First name must be specified") String firstName,
			@NotNull(message = "Last name must be specified") String lastName,
			@Size(min = 5, max = 20, message = "Name must be between {min} and {max} characters long.") @NotNull(message = "User name must be specified") String userName,
			@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,100}$", message = "Password must be at least 5 characters long and contain a lowercase, an upercase letter and a number") @Size(min = 5, message = "Password must be minimum {min} characters long.") @NotNull(message = "Password must be specified") String password,
			@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") @NotNull(message = "Email must be specified") String email,
			EUserRole userRole, Integer version, List<OfferEntity> offers, List<BillEntity> bills,
			List<VoucherEntity> voucher) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
		this.version = version;
		this.offers = offers;
		this.bills = bills;
		this.voucher = voucher;
	}






	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public EUserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(EUserRole userRole) {
		this.userRole = userRole;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public List<OfferEntity> getOffers() {
		return offers;
	}
	public void setOffers(List<OfferEntity> offers) {
		this.offers = offers;
	}
	public List<BillEntity> getBills() {
		return bills;
	}
	public void setBills(List<BillEntity> bills) {
		this.bills = bills;
	}
	public List<VoucherEntity> getVoucher() {
		return voucher;
	}
	public void setVoucher(List<VoucherEntity> voucher) {
		this.voucher = voucher;
	}
	


}
