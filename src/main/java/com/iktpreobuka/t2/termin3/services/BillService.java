package com.iktpreobuka.t2.termin3.services;

import java.util.Date;
import java.util.List;

import com.iktpreobuka.t2.termin3.entities.BillEntity;

public interface BillService {
    public Iterable<BillEntity> getAllByDatePeriod(Date startDate,Date endDate);
    public List<BillEntity> findCategoryBills(Integer categoryId);
    public List<BillEntity> canceledAll(Integer Id);
}
