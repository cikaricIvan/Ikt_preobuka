package com.iktpreobuka.t2.termin3.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import com.iktpreobuka.t2.termin3.entities.CategoryEntity;
import com.iktpreobuka.t2.termin3.entities.OfferEntity;
import com.iktpreobuka.t2.termin3.entities.OfferEntity.EUofferStatus;
import com.iktpreobuka.t2.termin3.entities.UserEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity.EUserRole;
import com.iktpreobuka.t2.termin3.repositories.CategoryRepository;
import com.iktpreobuka.t2.termin3.repositories.OfferRepository;
import com.iktpreobuka.t2.termin3.repositories.UserRepository;
import com.iktpreobuka.t2.termin3.security.Views;
import com.iktpreobuka.t2.termin3.services.BillService;
import com.iktpreobuka.t2.termin3.services.FileHandlerService;
import com.iktpreobuka.t2.termin3.services.OfferService;

@RestController
@RequestMapping(path="/api/v2/offers")
public class OfferController {
	
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private OfferService offerService;
	@Autowired
	private FileHandlerService fileHandlerService;
	@Autowired
	private BillService billService;
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
		String fieldName = "";
		String errorMessage = error.getDefaultMessage();
		if (error instanceof FieldError) {
		fieldName = ((FieldError) error).getField();
		}else {
			fieldName ="object-level";
		}
		errors.put(fieldName, errorMessage);
		});
		return errors;
		}
	
	
	
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.POST,path="/admin")
	public ResponseEntity<?> addOffer(@Valid   @RequestBody OfferEntity newOffer) {
		OfferEntity offer=new OfferEntity();
		offer.setOfferName(newOffer.getOfferName());
		offer.setOfferDescription(newOffer.getOfferDescription());
		offer.setOfferCreated(newOffer.getOfferCreated());
		offer.setOfferExpires(newOffer.getOfferExpires());
		offer.setRegularPrice(newOffer.getRegularPrice());
		offer.setActionPrice(newOffer.getActionPrice());
		offer.setImagePath(newOffer.getImagePath());
		offer.setAvailableOffer(newOffer.getAvailableOffer());
		offer.setBoughtOffer(newOffer.getBoughtOffer());
		offer.setOfferStatus(newOffer.getOfferStatus());
		offerRepository.save(offer);
		return new ResponseEntity<OfferEntity>(offer,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT,path="/{Id}")
	public OfferEntity updateOffer(@PathVariable Integer Id, @RequestBody OfferEntity updateOffer) {
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getId().equals(Id)) {
				if(updateOffer.getOfferName()!=null) {
					offer.setOfferName(updateOffer.getOfferName());
				}
				if(updateOffer.getOfferDescription()!=null) {
					offer.setOfferDescription(updateOffer.getOfferDescription());
				}
				if(updateOffer.getOfferCreated()!=null) {
					offer.setOfferCreated(updateOffer.getOfferCreated());
				}
				if(updateOffer.getOfferExpires()!=null) {
					offer.setOfferExpires(updateOffer.getOfferExpires());
				}
				if(updateOffer.getRegularPrice()!=null) {
					offer.setRegularPrice(updateOffer.getRegularPrice());
				}
				if(updateOffer.getActionPrice()!=null) {
					offer.setActionPrice(updateOffer.getActionPrice());
				}
				if(updateOffer.getImagePath()!=null) {
					offer.setImagePath(updateOffer.getImagePath());
				}
				if(updateOffer.getAvailableOffer()!=null) {
					offer.setAvailableOffer(updateOffer.getAvailableOffer());
				}
				if(updateOffer.getBoughtOffer()!=null) {
					offer.setBoughtOffer(updateOffer.getBoughtOffer());
				}
				offerRepository.save(offer);
				return offer;
			}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.DELETE,path="/{byId}")
	public OfferEntity deleteOffer(@PathVariable Integer byId) {
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getId().equals(byId)) {
				offerRepository.delete(offer);
				return offer;
			}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.GET,path="/{byId}")
	public OfferEntity findById(@PathVariable Integer byId ) {
        for(OfferEntity offer:offerRepository.findAll()) {
        	if(offer.getId().equals(byId)) {
        		return offerRepository.findById(byId).get();
        	}
        }
		return null;
	}
	@RequestMapping(method = RequestMethod.PUT,path="/changeOffer/{id}/status/{status}")
	public OfferEntity changeOffer(@PathVariable Integer id,@PathVariable EUofferStatus status,@RequestBody OfferEntity updateStatus) {
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getId().equals(id)&offer.getOfferStatus().equals(status)) {
				offer.setOfferStatus(updateStatus.getOfferStatus());
				if(offer.getOfferStatus()==EUofferStatus.EXPIRED) {
					billService.canceledAll(id);
				}
				offerRepository.save(offer);
				return offer;
			}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.GET,path="/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> findByPrice(@PathVariable Double lowerPrice,@PathVariable Double upperPrice) {
		List<OfferEntity> retVal=new ArrayList<OfferEntity>();
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getActionPrice()>lowerPrice&offer.getActionPrice()<upperPrice) {
				retVal.add(offer);
			}
		}
		return retVal;
	} 
	@RequestMapping(method = RequestMethod.POST,path="/{categoryId}/seller/{sellerId}")
	public OfferEntity updateOfferWithCategoryAndUser(@PathVariable Integer categoryId,@PathVariable Integer sellerId, @RequestBody OfferEntity entity){
	   UserEntity uE=userRepository.findById(sellerId).get();
	   if(!uE.getUserRole().equals(EUserRole.ROLE_SELLER)) {
		   return null;
	   }
		OfferEntity oE=entity;
		CategoryEntity cE=categoryRepository.findById(categoryId).get();
		oE.setUser(uE);
		oE.setCategory(cE);
		oE.setOfferName(oE.getOfferName());
		oE.setOfferDescription(oE.getOfferDescription());
		oE.setOfferCreated(oE.getOfferCreated());
		oE.setOfferExpires(oE.getOfferExpires());
		oE.setRegularPrice(oE.getRegularPrice());
		oE.setActionPrice(oE.getActionPrice());
		oE.setImagePath(oE.getImagePath());
		oE.setAvailableOffer(oE.getAvailableOffer());
		oE.setBoughtOffer(oE.getBoughtOffer());
		oE.setOfferStatus(oE.getOfferStatus());
		return offerRepository.save(oE); 
	}
	@RequestMapping(method = RequestMethod.POST,path="/{id}/category/{categoryId}")
	public OfferEntity updateCategoryInOffer(@PathVariable Integer id,@PathVariable Integer categoryId,@RequestBody OfferEntity updateOffer) {
		        OfferEntity offer=offerRepository.findById(id).get();
				CategoryEntity cE=categoryRepository.findById(categoryId).get();
				offer.setCategory(cE);
				if(updateOffer.getOfferName()!=null) {
					offer.setOfferName(updateOffer.getOfferName());
				}
				if(updateOffer.getOfferDescription()!=null) {
					offer.setOfferDescription(updateOffer.getOfferDescription());
				}
				if(updateOffer.getOfferCreated()!=null) {
					offer.setOfferCreated(updateOffer.getOfferCreated());
				}
				if(updateOffer.getOfferExpires()!=null) {
					offer.setOfferExpires(updateOffer.getOfferExpires());
				}
				if(updateOffer.getRegularPrice()!=null) {
					offer.setRegularPrice(updateOffer.getRegularPrice());
				}
				if(updateOffer.getActionPrice()!=null) {
					offer.setActionPrice(updateOffer.getActionPrice());
				}
				if(updateOffer.getImagePath()!=null) {
					offer.setImagePath(updateOffer.getImagePath());
				}
				if(updateOffer.getAvailableOffer()!=null) {
					offer.setAvailableOffer(updateOffer.getAvailableOffer());
				}
				if(updateOffer.getBoughtOffer()!=null) {
					offer.setBoughtOffer(updateOffer.getBoughtOffer());
				}
				return offerRepository.save(offer);	
	}
	@RequestMapping(method = RequestMethod.PUT,path="/changeOffer/{Id}")
	public OfferEntity changeAvailableBoughtOffer(@PathVariable Integer Id) {
		OfferEntity offer=offerRepository.findById(Id).get();
		offerService.changeAvailableBoughtOffer(Id);
		return offer;
	}
	@RequestMapping(method = RequestMethod.POST,path="/uploadImage/{id}")
	public OfferEntity upload(@PathVariable Integer id,@RequestParam ("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getId().equals(id)) {
				String imagePath=fileHandlerService.simpleFileUpload(file, redirectAttributes);
				offer.setImagePath(imagePath);	
			return offerRepository.save(offer);		
		}
		}
	return null;
	}
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,path="/public")
    public Iterable<OfferEntity> findAllOffers() {
    	return offerRepository.findAll();
    }
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET,path="/private")
    public Iterable<OfferEntity> findAllOffersPrivate() {
    	return offerRepository.findAll();
    }
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET,path="/admin")
    public Iterable<OfferEntity> findAllOffersAdmin() {
    	return offerRepository.findAll();
    }
}