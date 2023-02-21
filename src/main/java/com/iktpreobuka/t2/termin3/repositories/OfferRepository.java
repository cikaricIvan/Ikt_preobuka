package com.iktpreobuka.t2.termin3.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.t2.termin3.entities.OfferEntity;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer> {

	OfferEntity findByAvailableOffer(Integer availableOffer);

}
