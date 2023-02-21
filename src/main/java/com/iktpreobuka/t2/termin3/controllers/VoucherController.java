package com.iktpreobuka.t2.termin3.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.iktpreobuka.t2.termin3.dto.VoucherDTO;
import com.iktpreobuka.t2.termin3.entities.OfferEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity.EUserRole;
import com.iktpreobuka.t2.termin3.entities.VoucherEntity;
import com.iktpreobuka.t2.termin3.repositories.OfferRepository;
import com.iktpreobuka.t2.termin3.repositories.UserRepository;
import com.iktpreobuka.t2.termin3.repositories.VoucherRepository;
import com.iktpreobuka.t2.termin3.security.Views;

@RestController
@RequestMapping(path = "/api/v2/vouchers")
public class VoucherController {

	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OfferRepository offerRepository;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = "";
			String errorMessage = error.getDefaultMessage();
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
			} else {
				fieldName = "object-level";
			}
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addVoucher(@Valid @RequestBody VoucherDTO addVoucher) {
		VoucherEntity voucher = new VoucherEntity();
		voucher.setExpirationDate(addVoucher.getExpirationDate());
		voucher.setIsUsed(addVoucher.getIsUsed());
		voucherRepository.save(voucher);
		return new ResponseEntity<>(voucher, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{byId}")
	public VoucherEntity deleteVoucher(@PathVariable Integer byId) {
		for (VoucherEntity voucher : voucherRepository.findAll()) {
			if (voucher.getId().equals(byId)) {
				voucherRepository.delete(voucher);
				return voucher;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{byId}")
	public VoucherEntity findById(@PathVariable Integer byId) {
		for (VoucherEntity voucher : voucherRepository.findAll()) {
			if (voucher.getId().equals(byId)) {
				return voucherRepository.findById(byId).get();
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/{offerId}/buyer/{buyerId}")
	public VoucherEntity newVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId,
			@RequestBody VoucherEntity entity) {
		UserEntity uE = userRepository.findById(buyerId).get();
		if (!uE.getUserRole().equals(EUserRole.ROLE_CUSTOMER)) {
			return null;
		}
		VoucherEntity vE = entity;
		OfferEntity oE = offerRepository.findById(offerId).get();
		vE.setUser(uE);
		vE.setOffer(oE);
		vE.setExpirationDate(vE.getExpirationDate());
		vE.setIsUsed(vE.getIsUsed());

		return voucherRepository.save(vE);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{byId}")
	public VoucherEntity updateVoucher(@PathVariable Integer byId, @RequestBody VoucherEntity updateVoucher) {
		VoucherEntity voucher = voucherRepository.findById(byId).get();
		if (updateVoucher.getExpirationDate() != null) {
			voucher.setExpirationDate(updateVoucher.getExpirationDate());
		}
		if (updateVoucher.getIsUsed() != null) {
			voucher.setIsUsed(updateVoucher.getIsUsed());
		}
		return voucherRepository.save(voucher);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findByBuyer/{buyerId}")
	public Iterable<VoucherEntity> findByBuyer(@PathVariable Integer buyerId) {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(buyerId)) {
				return voucherRepository.findByUser(user);
			}
		}
		return null;

	}

	@RequestMapping(method = RequestMethod.GET, path = "/findByOffer/{offerId}")
	public Iterable<VoucherEntity> findByOffer(@PathVariable Integer offerId) {
		for (OfferEntity offer : offerRepository.findAll()) {
			if (offer.getId().equals(offerId)) {
				return voucherRepository.findByOffer(offer);
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findNonExpiredVoucher/{actualDate}")
	public Iterable<VoucherEntity> findNonExpiredVoucher(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date actualDate) {
		List<VoucherEntity> retVal = new ArrayList<VoucherEntity>();
		for (VoucherEntity voucher : voucherRepository.findAll()) {
			if (voucher.getExpirationDate().before(actualDate)) {
				retVal.add(voucher);

			}
		}

		return retVal;
	}

	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, path = "/public")
	public Iterable<VoucherEntity> findAllVoucher() {
		return voucherRepository.findAll();
	}

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, path = "/private")
	public Iterable<VoucherEntity> findAllVoucherPrivate() {
		return voucherRepository.findAll();
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, path = "/admin")
	public Iterable<VoucherEntity> findAllVoucherAdmin() {
		return voucherRepository.findAll();
	}
}
