package com.iktpreobuka.t2.termin3.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import com.iktpreobuka.t2.termin3.entities.UserEntity;
import com.iktpreobuka.t2.termin3.entities.UserEntity.EUserRole;
import com.iktpreobuka.t2.termin3.repositories.UserRepository;
import com.iktpreobuka.t2.termin3.security.Views;
import com.iktpreobuka.t2.termin3.services.UserService;

@RestController
@RequestMapping(path = "/api/v2/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public UserEntity addUser(@Valid @RequestBody UserEntity newUser) {
		UserEntity user = new UserEntity();
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setUserName(newUser.getUserName());
		user.setPassword(newUser.getPassword());
		user.setEmail(newUser.getEmail());
		user.setUserRole(newUser.getUserRole());
		userRepository.save(user);
		return user;
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, path = "/admin/{byId}")
	public ResponseEntity<?> findById(@PathVariable Integer byId) {	
		return userService.findById(byId);
	}
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, path = "/public/{byId}")
	public ResponseEntity<?> findByIdPublic(@PathVariable Integer byId) {
		return userService.findByIdPublic(byId);
	}
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, path = "/private/{byId}")
	public ResponseEntity<?> findByIdPrivate(@PathVariable Integer byId) {
		return userService.findByIdPrivate(byId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserEntity addUserWithRole(@RequestBody UserEntity newUser) {
		UserEntity user = new UserEntity();
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setUserName(newUser.getUserName());
		user.setPassword(newUser.getPassword());
		user.setEmail(newUser.getEmail());
		user.setUserRole(EUserRole.ROLE_CUSTOMER);
		userRepository.save(user);
		return user;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{Id}")
	public UserEntity updateUserWhithoutPassword(@PathVariable Integer Id, @RequestBody UserEntity updateUser) {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(Id)) {
				if (updateUser.getFirstName() != null) {
					user.setFirstName(updateUser.getFirstName());
				}
				if (updateUser.getLastName() != null) {
					user.setLastName(updateUser.getLastName());
				}
				if (updateUser.getUserName() != null) {
					user.setUserName(updateUser.getUserName());
				}
				if (updateUser.getEmail() != null) {
					user.setEmail(updateUser.getEmail());
				}
				userRepository.save(user);
				return user;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/change/{id}/role/{role}")
	public UserEntity updateUserRole(@PathVariable Integer id, @PathVariable EUserRole role,
			@RequestBody UserEntity updateUserRole) {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(id) & user.getUserRole().equals(role)) {
				user.setUserRole(updateUserRole.getUserRole());
				userRepository.save(user);
				return user;
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/changepassword/{Id}")
	public UserEntity changePassword(@PathVariable Integer Id, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(Id)) {
				if (user.getPassword().equals(oldPassword)) {
					user.setPassword(newPassword);
					userRepository.save(user);
					return user;
				} else
					return null;
			}
		}
		return null;
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, path = "/admin/{Id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer Id) {
		return userService.deleteUser(Id);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
	public UserEntity findByUserName(@PathVariable String username) {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getUserName().equals(username)) {
				return userRepository.findByUserName(username);
			}
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/admin")
	@JsonView(Views.Admin.class)
	public Iterable<UserEntity> findAll() {
		return userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/public")
	@JsonView(Views.Public.class)
	public Iterable<UserEntity> findAllPublic() {
		return userRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/private")
	@JsonView(Views.Private.class)
	public Iterable<UserEntity> findAllPrivate() {
		return userRepository.findAll();
	}
}
