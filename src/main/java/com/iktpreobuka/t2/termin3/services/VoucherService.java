package com.iktpreobuka.t2.termin3.services;

import com.iktpreobuka.t2.termin3.entities.BillEntity;
import com.iktpreobuka.t2.termin3.entities.VoucherEntity;

public interface VoucherService {
	public VoucherEntity createVoucher(BillEntity bill);

}
