package com.lucasapds.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasapds.finance.entities.User;
import com.lucasapds.finance.exception.ErrorAuthenticate;
import com.lucasapds.finance.exception.RusinessRuleException;
import com.lucasapds.finance.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository repository;

	@Autowired
	public UserService(UserRepository repository) {

		this.repository = repository;
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.get();

	}

	public User authenticate(String email, String password) {

		Optional<User> user = repository.findByEmail(email);

		if (!user.isPresent()) {
			throw new ErrorAuthenticate("usuario nao encontrado para o email informado");
		}

		if (!user.get().getPassword().equals(password)) {
			throw new ErrorAuthenticate("Senha inválida");
		}

		return user.get();
	}

	@Transactional
	public User saveUser(User user) {
		validateEmail(user.getEmail());
		return repository.save(user);
	}

	public void validateEmail(String email) {

		boolean exists = repository.existsByEmail(email);
		if(exists) {
			throw new RusinessRuleException("Já existe um usuário cadastrado com este email");
		}

	}

}
