package com.iktpreobuka.t2.termin3.services;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.t2.termin3.entities.CategoryEntity;
import com.iktpreobuka.t2.termin3.entities.OfferEntity;
import com.iktpreobuka.t2.termin3.repositories.CategoryRepository;
import com.iktpreobuka.t2.termin3.repositories.OfferRepository;
@Service
public class OfferServiceImpl implements OfferService {
	
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private CategoryRepository  categoryRepository;

	@Override
	public OfferEntity changeAvailableBoughtOffer(Integer Id) {
		OfferEntity offer=offerRepository.findById(Id).get();
		offer.setAvailableOffer(offer.getAvailableOffer()-1);
		offer.setBoughtOffer(offer.getBoughtOffer()+1);
		offerRepository.save(offer);
		return offer;
	}

	@Override
	public OfferEntity changeAvailableBoughtOfferCanceled(Integer Id) {
		OfferEntity offer=offerRepository.findById(Id).get();
		offer.setAvailableOffer(offer.getAvailableOffer()+1);
		offer.setBoughtOffer(offer.getBoughtOffer()-1);
		offerRepository.save(offer);
		return null;
	}

	@Override
	public List<OfferEntity> findCategoryOffers(Integer categoryId) {
		CategoryEntity category=categoryRepository.findById(categoryId).get();
		List<OfferEntity> allOffers=category.getOffers();
		List<OfferEntity> activeOffers=new ArrayList<>();
         LocalDate date=LocalDate.now();
		for(OfferEntity offer:allOffers) {
			if(offer.getOfferExpires().compareTo(date)>0) { 
				activeOffers.add(offer);
			}
		}
		return activeOffers;
	}
	
	}
	


