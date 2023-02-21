package com.iktpreobuka.t2.termin3.services;

import org.springframework.http.ResponseEntity;



public interface UserService {
public ResponseEntity<?> findById(Integer byId);
public ResponseEntity<?> findByIdPublic(Integer byId);
public ResponseEntity<?> findByIdPrivate(Integer byId);
public ResponseEntity<?> deleteUser(Integer Id);
}
