package com.iktpreobuka.t2.termin3.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.t2.termin3.entities.BillEntity;
import com.iktpreobuka.t2.termin3.entities.VoucherEntity;
import com.iktpreobuka.t2.termin3.repositories.VoucherRepository;
@Service
public class VoucherServiceImpl implements VoucherService {
	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private EmailService emailService;

	@Override
	public VoucherEntity createVoucher(BillEntity bill) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE,30);
		VoucherEntity voucher=new VoucherEntity();
		voucher.setUser(bill.getUser());
		voucher.setOffer(bill.getOffer());
		voucher.setExpirationDate(cal.getTime());
		voucher.setIsUsed(true);
		emailService.sendVoucher(voucher);
		return voucherRepository.save(voucher);
	}

}
