package com.iktpreobuka.t2.termin3.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.t2.termin3.entities.BillEntity;
import com.iktpreobuka.t2.termin3.entities.CategoryEntity;
import com.iktpreobuka.t2.termin3.entities.OfferEntity;
import com.iktpreobuka.t2.termin3.repositories.BillRepository;
import com.iktpreobuka.t2.termin3.repositories.CategoryRepository;

@Service
public class BillServiceImpl implements BillService {
	
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Iterable<BillEntity> getAllByDatePeriod(Date startDate, Date endDate) {
		 List<BillEntity> retVal=new ArrayList<BillEntity>();
		 for(BillEntity bill:billRepository.findAll()) {
			 if(bill.getBillCreated().after(startDate)&bill.getBillCreated().before(endDate)) {
				 retVal.add(bill);
				 
			 }
		 }
		
		return retVal;
		
	}

	@Override
	public List<BillEntity> findCategoryBills(Integer categoryId) {
	CategoryEntity category=categoryRepository.findById(categoryId).get();
	List<OfferEntity> allOffers=category.getOffers();
	List<BillEntity> activeBills=new ArrayList<>();
	for(OfferEntity offer:allOffers) {
		List<BillEntity> allBills=offer.getBill();
		 for(BillEntity bill:allBills) {
			  if ((bill.getPaymentMade()==true)&& (bill.getPaymentCanceled()==false)) {
				  activeBills.add(bill);
			  }
				
			  }
	}
		return activeBills;
	
	}

	@Override
	public List<BillEntity> canceledAll(Integer Id) {
          List<BillEntity> bills=billRepository.findAllByOfferId(Id);
          for(BillEntity bill:bills) {
        	  bill.setPaymentCanceled(true);
        	  bill.setPaymentMade(false);
          }
		return bills;
	}

}
