package com.iktpreobuka.t2.termin3.controllers;




import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.t2.termin3.dto.BillDTO;
import com.iktpreobuka.t2.termin3.entities.BillEntity;

import com.iktpreobuka.t2.termin3.entities.OfferEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity;
import com.iktpreobuka.t2.termin3.repositories.BillRepository;

import com.iktpreobuka.t2.termin3.repositories.OfferRepository;
import com.iktpreobuka.t2.termin3.repositories.UserRepository;
import com.iktpreobuka.t2.termin3.security.Views;
import com.iktpreobuka.t2.termin3.services.BillService;
import com.iktpreobuka.t2.termin3.services.OfferService;
import com.iktpreobuka.t2.termin3.services.VoucherService;

@RestController
@RequestMapping(path="/api/v2/bills")
public class BillController {
	  @Autowired
      private BillRepository billRepository;
	  @Autowired
	  private OfferRepository offerRepository;
	  @Autowired
	  private UserRepository userRepository;
	  @Autowired
	  private OfferService offerService;
	  @Autowired
	  private BillService billService;
	  @Autowired
	  private VoucherService voucherService;
	  
	  

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

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addBill(@Valid     @RequestBody BillDTO newBill) {
		BillEntity bill=new BillEntity();
		bill.setPaymentMade(newBill.getPaymentMade());
		bill.setPaymentCanceled(newBill.getPaymentCanceled());
		bill.setBillCreated(newBill.getBillCreated());
		billRepository.save(bill);
		return new ResponseEntity<>(bill,HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE,path="/{byId}")
	public BillEntity deleteBill(@PathVariable Integer byId) {
		for (BillEntity bill:billRepository.findAll()){
		if(bill.getId().equals(byId)) {
		billRepository.delete(bill);
		return bill;
		}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.POST,path="/{offerId}/buyer/{buyerId}")
	public BillEntity addNewBill(@PathVariable Integer offerId,@PathVariable Integer buyerId,@RequestBody BillEntity addNewBill) {
		BillEntity bill=addNewBill;
		OfferEntity oE=offerRepository.findById(offerId).get();
		 oE=offerService.changeAvailableBoughtOffer(offerId);
		UserEntity uE=userRepository.findById(buyerId).get();
		bill.setOffer(oE);
		bill.setUser(uE);
		bill.setPaymentMade(bill.getPaymentMade());
		bill.setPaymentCanceled(bill.getPaymentCanceled());
		bill.setBillCreated(bill.getBillCreated());
		billRepository.save(bill);
		return bill;
	}
	@RequestMapping(method = RequestMethod.PUT,path="/{byId}")
	public BillEntity updateBill(@PathVariable Integer byId,@RequestBody BillEntity updateBill) {
		for(BillEntity bill:billRepository.findAll()) {
			if(bill.getId().equals(byId)) {
				if(updateBill.getPaymentMade()==true) {
					bill.setPaymentMade(true);
					voucherService.createVoucher(bill);
					
				}
				if(updateBill.getPaymentCanceled()!=null) {
					bill.setPaymentCanceled(updateBill.getPaymentCanceled());
					if(updateBill.getPaymentCanceled()==true) {
						offerService.changeAvailableBoughtOfferCanceled(bill.getOffer().getId());
					}
				}
				if(updateBill.getBillCreated()!=null) {
					bill.setBillCreated(updateBill.getBillCreated());
				}
				return billRepository.save(bill);
			}
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.GET,path="/findByBuyer/{buyerId}")
	public Iterable<BillEntity> findBillByUserId(@PathVariable Integer buyerId){
		UserEntity user=userRepository.findById(buyerId).get();
		if(user.getId().equals(buyerId)) {
		return billRepository.findByUser(user);
		}
		return null;
	}
	@RequestMapping(method = RequestMethod.GET,path="/findByCategory/{categoryId}")
	public Iterable<BillEntity> findByCategory(@PathVariable Integer categoryId){
		return billRepository.findByOfferCategoryId(categoryId);
	}
	@RequestMapping(method = RequestMethod.GET,path="/findByDate/{startDate}/and/{endDate}")
	public Iterable<BillEntity> findByDate(@PathVariable @DateTimeFormat (pattern = "yyyy-MM-dd") Date startDate,@PathVariable @DateTimeFormat (pattern = "yyyy-MM-dd") Date endDate){
		return billService.getAllByDatePeriod(startDate, endDate);
	} 
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,path="/public")
	public Iterable<BillEntity> findAllBill(){
		return billRepository.findAll();
	}
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET,path="/private")
	public Iterable<BillEntity> findAllBillPrivate(){
		return billRepository.findAll();
	}
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET,path="/admin")
	public Iterable<BillEntity> findAllBillAdmin(){
		return billRepository.findAll();
	}
}
