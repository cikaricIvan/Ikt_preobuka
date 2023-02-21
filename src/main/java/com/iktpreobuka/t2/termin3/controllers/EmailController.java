package com.iktpreobuka.t2.termin3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.t2.termin3.entities.VoucherEntity;
import com.iktpreobuka.t2.termin3.services.EmailService;

@RestController
@RequestMapping(path="/")
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	public String sendVoucher(VoucherEntity voucher) {
		if(voucher==null) {
			return null;
		}
		emailService.sendVoucher(voucher);
		return "Your mail has been sent!";
	}
	

}
