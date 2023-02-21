package com.iktpreobuka.t2.termin3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.entities.CategoryEntity;
import com.iktpreobuka.t2.termin3.repositories.CategoryRepository;
import com.iktpreobuka.t2.termin3.security.Views;
import com.iktpreobuka.t2.termin3.services.BillService;
import com.iktpreobuka.t2.termin3.services.OfferService;

@RestController
@RequestMapping(path="/api/v2/categories")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private OfferService offerService;
	@Autowired
	private BillService billService;
	
	@RequestMapping(method = RequestMethod.POST)
	public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
		CategoryEntity category=new CategoryEntity();
		category.setCategoryName(newCategory.getCategoryName());
		category.setCategoryDescription(newCategory.getCategoryDescription());
		categoryRepository.save(category);
		return category;
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/{byId}")
	public CategoryEntity findById(@PathVariable Integer byId) {
		for(CategoryEntity category:categoryRepository.findAll()) {
			if(category.getId().equals(byId)) {
				return categoryRepository.findById(byId).get();
			}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.PUT,path="/{byId}")
	public CategoryEntity updateCategory(@PathVariable Integer byId,@RequestBody CategoryEntity updatedCategory) {
		for(CategoryEntity category:categoryRepository.findAll()) {
			if(category.getId().equals(byId)) {
				if(updatedCategory.getCategoryName()!=null) {
					category.setCategoryName(updatedCategory.getCategoryName());
				}
				if(updatedCategory.getCategoryDescription()!=null) {
					category.setCategoryDescription(updatedCategory.getCategoryDescription());
				}
				categoryRepository.save(category);
				return category;
			}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.DELETE,path="/{byId}")
	public CategoryEntity deleteCategory(@PathVariable Integer byId) {
		for(CategoryEntity category:categoryRepository.findAll()) {
			if(category.getId().equals(byId)) {
				if(offerService.findCategoryOffers(byId).isEmpty()&&billService.findCategoryBills(byId).isEmpty()) {
					categoryRepository.delete(category);
					return category;
				}
				
			}
		}
		return null;
	}
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,path="/public")
	public Iterable<CategoryEntity> getAllCategories(){
		return categoryRepository.findAll();
	}
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET,path="/private")
	public Iterable<CategoryEntity> getAllCategoriesPrivate(){
		return categoryRepository.findAll();
	}
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET,path="/admin")
	public Iterable<CategoryEntity> getAllCategoriesAdmin(){
		return categoryRepository.findAll();
	}

}
