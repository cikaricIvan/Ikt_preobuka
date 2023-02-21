package com.iktpreobuka.t2.termin3.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.t2.termin3.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	UserEntity findByUserName(String username);


}
