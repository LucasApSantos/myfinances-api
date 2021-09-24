package com.lucasapds.finance.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasapds.finance.entities.Launch;
import com.lucasapds.finance.enums.LaunchStatus;
import com.lucasapds.finance.exception.RusinessRuleException;
import com.lucasapds.finance.repositories.LaunchRepository;

@Service
public class LaunchService {

	private LaunchRepository repository;

	@Autowired
	public LaunchService(LaunchRepository repository) {
		this.repository = repository;
	}

	public List<Launch> findAll() {
		return repository.findAll();
	}

	public Launch findById(Long id) {
		Optional<Launch> obj = repository.findById(id);
		return obj.get();

	}

	@Transactional
	public Launch save(Launch launch) {
		validate(launch);
		launch.setStatus(LaunchStatus.PENDING);
		return repository.save(launch);
	}

	@Transactional
	public Launch update(Launch launch) {
		Objects.requireNonNull(launch.getId());
		validate(launch);
		return repository.save(launch);
	}

	@Transactional
	public void delete(Launch launch) {
		Objects.requireNonNull(launch.getId());
		repository.delete(launch);

	}

	@Transactional(readOnly = true)
	public List<Launch> search(Launch launch) {

		Example example = Example.of(launch,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return repository.findAll(example);
	}

	public void updateStatus(Launch launch, LaunchStatus status) {

		launch.setStatus(status);
		update(launch);

	}

	public void validate(Launch launch) {

		if (launch.getDescription() == null || launch.getDescription().trim().equals("")) {
			throw new RusinessRuleException("Informe uma descrição válida");
		}

		if (launch.getMonth() == null || launch.getMonth() < 1 || launch.getMonth() > 12) {
			throw new RusinessRuleException("Informe um mês válido");
		}
		if (launch.getYear() == null || launch.getYear().toString().length() != 4) {
			throw new RusinessRuleException("Informe um ano válido");
		}
		if (launch.getUser() == null || launch.getUser().getId() == null) {
			throw new RusinessRuleException("Informe um usuário");
		}
		if (launch.getValue() == null || launch.getValue().compareTo(BigDecimal.ZERO) < 1) {
			throw new RusinessRuleException("Informe um valor válido");
		}
		if (launch.getType() == null) {
			throw new RusinessRuleException("Informe um tipo de lançamento");
		}
	}

}
