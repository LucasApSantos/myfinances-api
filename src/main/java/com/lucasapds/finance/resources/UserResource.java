package com.lucasapds.finance.resources;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasapds.finance.api.dto.UserDTO;
import com.lucasapds.finance.entities.User;
import com.lucasapds.finance.exception.ErrorAuthenticate;
import com.lucasapds.finance.exception.RusinessRuleException;
import com.lucasapds.finance.services.LaunchService;
import com.lucasapds.finance.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserResource {

	private final UserService service;
	private final LaunchService launchService;

	@GetMapping(value = "/users/")
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/users/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
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

	@PostMapping
	public ResponseEntity<Serializable> save(@RequestBody UserDTO dto) {

		User user = new User(null, dto.getName(), dto.getEmail(), dto.getPassword());

		try {

			User userSave = service.saveUser(user);
			return new ResponseEntity<Serializable>(userSave, HttpStatus.CREATED);

		} catch (RusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(value = "/users/{id}/balance")
	public ResponseEntity getBalance(@PathVariable ("id") Long id) {
		Optional<User> user = service.getById(id);
		if(!user.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		BigDecimal balance = launchService.getBanlanceByUser(id);
		return ResponseEntity.ok(balance);
		
	}

}
