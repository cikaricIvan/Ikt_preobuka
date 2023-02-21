package com.iktpreobuka.t2.termin3.repositories;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.t2.termin3.entities.BillEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	Iterable<BillEntity> findByUser(UserEntity user);

	Iterable<BillEntity> findByOfferCategoryId(Integer categoryId);

	List<BillEntity> findAllByOfferId(Integer id);




}
