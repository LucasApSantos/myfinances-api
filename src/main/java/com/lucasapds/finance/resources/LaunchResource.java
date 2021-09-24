package com.lucasapds.finance.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasapds.finance.entities.Launch;
import com.lucasapds.finance.services.LaunchService;

@RestController
@RequestMapping(value = "/launchs")
public class LaunchResource {

	@Autowired
	private LaunchService service;

	@GetMapping
	public ResponseEntity<List<Launch>> findAll() {
		List<Launch> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Launch> findById(@PathVariable Long id){
		Launch obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
 }
