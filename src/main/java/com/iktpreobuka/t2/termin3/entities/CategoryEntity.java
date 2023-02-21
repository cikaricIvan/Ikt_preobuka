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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CategoryEntity {
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull(message="Name must be specified")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String categoryName;
	@Size(max=50,message = "Category description max  {max} characters long.")
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String  categoryDescription;
	@Version
	private Integer version;
	@JsonView(Views.Public.class)
	@JsonIgnore
	@OneToMany(mappedBy="category",cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	private List<OfferEntity> offers=new ArrayList<OfferEntity>();
	
	
	public CategoryEntity() {
		super();
		// TODO Auto-generated constructor stub
		
	}
	
	public CategoryEntity(Integer id, String categoryName, String categoryDescription, Integer version,
			List<OfferEntity> offers) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.version = version;
		this.offers = offers;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
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
	
   
}
