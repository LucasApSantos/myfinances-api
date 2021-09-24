package com.lucasapds.finance.resources;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasapds.finance.api.dto.UserDTO;
import com.lucasapds.finance.entities.User;
import com.lucasapds.finance.exception.ErrorAuthenticate;
import com.lucasapds.finance.exception.RusinessRuleException;
import com.lucasapds.finance.services.UserService;

@RestController
public class UserResource {

	private UserService service;

	
	@RequestMapping(value = "/users")
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/users/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	public UserResource(UserService service) {
		this.service = service;
	}

	@RequestMapping(value = "/auth")
	public ResponseEntity<Serializable> authenticate(@RequestBody UserDTO dto) {

		try {

			User userAuthenticate = service.authenticate(dto.getEmail(), dto.getPassword());
			return ResponseEntity.ok(userAuthenticate);

		} catch (ErrorAuthenticate e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@RequestMapping(value = "/create")
	public ResponseEntity<Serializable> save(@RequestBody UserDTO dto) {

		User user = new User(null, dto.getName(), dto.getEmail(), dto.getPassword());

		try {

			User userSave = service.saveUser(user);
			return new ResponseEntity<Serializable>(userSave, HttpStatus.CREATED);

		} catch (RusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
