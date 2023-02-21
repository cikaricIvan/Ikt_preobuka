package com.iktpreobuka.t2.termin3.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.t2.termin3.entities.VoucherEntity;
@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
public JavaMailSender emailSender;
	@Override
	public void sendVoucher(VoucherEntity voucher) {
		MimeMessage mail=emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper= new MimeMessageHelper(mail,true);
			helper.setTo(voucher.getUser().getEmail());
			helper.setSubject("voucher"+voucher.getId());
			String text="<html><body><table"
					+ "	<head>\r\n"
					+ "<title> Voucher </title>\r\n"
					+ "	</head>\r\n"
					+ "	<body>\r\n"
					+ "	<h1> Voucher  </h1>\r\n"
					+ "	<p> Voucher for your purchase</p>\r\n"
					+ "	<table>\r\n"
					+ " 	 <tr>\r\n"
					+ "    	<th>Buyer</th>\r\n"
					+ "    	<th>Offer</th>\r\n"
					+ "    	<th>Price</th>\r\n"
					+ "    	<th>Expires Date</th>\r\n"
					+ " 	 </tr>\r\n"
					+ "  	<tr>\r\n"
					+ "    	<td>"  + voucher.getUser().getFirstName() + " " + voucher.getUser().getLastName()  + " </td>\r\n"
					+ "    	<td>"  + voucher.getOffer().getOfferName() +  "</td>\r\n"
					+ "    	<td>"  + voucher.getOffer().getActionPrice() +  "</td>\r\n"
					+ "    	<td>"  + voucher.getExpirationDate()  +     "</td>\r\n"
					+ "  	</tr>\r\n"
					+ "</table>\r\n"
					+ "	</body>\r\n"
					+ "</html>";
			helper.setText(text, true);
			emailSender.send(mail);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
