package com.lucasapds.finance.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasapds.finance.repositories.UserRepository;
import com.lucasapds.finance.services.UserService;

@RestController
@RequestMapping(value = "/verifica")
public class Test {

	@Autowired
	private UserService service;
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/{email}")
	public String findByEmail(@PathVariable String email) {

		
		
		return null;
		

	}
	
	
}
