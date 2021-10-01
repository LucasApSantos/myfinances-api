package com.lucasapds.finance.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasapds.finance.api.dto.LaunchDTO;
import com.lucasapds.finance.api.dto.UpdateStatusDTO;
import com.lucasapds.finance.entities.Launch;
import com.lucasapds.finance.entities.User;
import com.lucasapds.finance.enums.LaunchStatus;
import com.lucasapds.finance.enums.LaunchType;
import com.lucasapds.finance.exception.RusinessRuleException;
import com.lucasapds.finance.services.LaunchService;
import com.lucasapds.finance.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/launchs")
@RequiredArgsConstructor
public class LaunchResource {

	private final LaunchService launchService;
	private final UserService userService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Launch>> findAll() {
		List<Launch> list = launchService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Launch> findById(@PathVariable Long id) {
		Launch obj = launchService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity search(
			@RequestParam(value ="description", required = false) String description,
			@RequestParam(value ="month", required = false) Integer month,
			@RequestParam(value ="year", required = false) Integer year,
			@RequestParam("user") Long userId
			) {
		Launch launch = new Launch();
		launch.setDescription(description);
		launch.setMonth(month);
		launch.setYear(year);
		
		
		Optional<User> user = userService.getById(userId);
		if(!user.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta");
		}else {
			launch.setUser(user.get());
		}
		
		List<Launch> launchs = launchService.search(launch);
		return ResponseEntity.ok(launchs);
	}

	@PostMapping
	public ResponseEntity save(@RequestBody LaunchDTO dto) {

		try {

			Launch entity = convert(dto);
			entity = launchService.save(entity);
			return new ResponseEntity(entity, HttpStatus.CREATED);

		} catch (RusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody LaunchDTO dto) {
		return launchService.getById(id).map(entity -> {

			try {
				Launch launch = convert(dto);
				launch.setId(entity.getId());
				launchService.update(launch);
				return ResponseEntity.ok(launch);
			} catch (RusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		}).orElseGet(() ->

		new ResponseEntity("Lançamento não entrado na base de dados", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping(value = "{id}/update-status")
	public ResponseEntity updateStatus(@PathVariable("id") Long id, @RequestBody UpdateStatusDTO dto) {
		return launchService.getById(id).map( entity -> {
			LaunchStatus selectStatus = LaunchStatus.valueOf(dto.getStatus());
			if(selectStatus == null) {
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o status do lançamento");
			}
			
			try {
				
				entity.setStatus(selectStatus);
				launchService.update(entity);
				return ResponseEntity.ok(entity);
				
			}catch (RusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}).orElseGet(() ->

		new ResponseEntity("Lançamento não entrado na base de dados", HttpStatus.BAD_REQUEST));
		
	}

	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		return launchService.getById(id).map(entity -> {
			launchService.delete(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não entrado na base de dados", HttpStatus.BAD_REQUEST));

	}

	private Launch convert(LaunchDTO dto) {
		Launch launch = new Launch();
		launch.setId(dto.getId());
		launch.setDescription(dto.getDescription());
		launch.setYear(dto.getYear());
		launch.setMonth(dto.getMonth());
		launch.setValue(dto.getValue());

		User user = userService.getById(dto.getUser())
				.orElseThrow(() -> new RusinessRuleException("Usuário não encontrado para o Id informado"));
		launch.setUser(user);
		if(dto.getType() != null) {
			launch.setType(LaunchType.valueOf(dto.getType()));
		}
		
		if(dto.getStatus() != null) {
			launch.setStatus(LaunchStatus.valueOf(dto.getStatus()));
		}
		
		return launch;
	}

}
