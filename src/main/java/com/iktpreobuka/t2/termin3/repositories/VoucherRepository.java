package com.iktpreobuka.t2.termin3.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.t2.termin3.entities.OfferEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity;
import com.iktpreobuka.t2.termin3.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {

	Iterable<VoucherEntity> findByUser(UserEntity user);

	Iterable<VoucherEntity> findByOffer(OfferEntity offer);







	

}
