package com.iktpreobuka.t2.termin3.services;


import java.util.List;

import com.iktpreobuka.t2.termin3.entities.OfferEntity;

public interface OfferService {
 public OfferEntity changeAvailableBoughtOffer(Integer Id);
 public OfferEntity changeAvailableBoughtOfferCanceled(Integer Id);
 public List <OfferEntity> findCategoryOffers(Integer categoryId);
}
