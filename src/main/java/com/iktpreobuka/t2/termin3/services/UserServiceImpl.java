package com.iktpreobuka.t2.termin3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.t2.termin3.controllers.util.RESTError;
import com.iktpreobuka.t2.termin3.entities.UserEntity;
import com.iktpreobuka.t2.termin3.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> findById(Integer byId) {
		if (byId < 0) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Id must be greater or equal to "),
					HttpStatus.BAD_REQUEST);
		}
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(byId)) {
				userRepository.findById(byId).get();
				return new ResponseEntity<UserEntity>(user, HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User with this Id not found"), HttpStatus.NOT_FOUND);
	}
	@Override
	public ResponseEntity<?> findByIdPublic(Integer byId) {
		if (byId < 0) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Id must be greater or equal to"),
					HttpStatus.BAD_REQUEST);
		}
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(byId)) {
				userRepository.findById(byId).get();
				return new ResponseEntity<UserEntity>(user, HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User with this Id not found"), HttpStatus.NOT_FOUND);
	}
	@Override
	public ResponseEntity<?> findByIdPrivate(Integer byId) {
		if (byId < 0) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Id must be greater or equal to"),
					HttpStatus.BAD_REQUEST);
		}
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(byId)) {
				userRepository.findById(byId).get();
				return new ResponseEntity<UserEntity>(user, HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User with this Id not found"), HttpStatus.NOT_FOUND);
	}
	@Override
	public ResponseEntity<?> deleteUser(Integer Id) {
		if (Id < 0) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Id must be greater or equal to "),
					HttpStatus.BAD_REQUEST);
		}
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(Id)) {
				userRepository.delete(user);
				return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
			}
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User with this Id not found"), HttpStatus.NOT_FOUND);
	}
}
