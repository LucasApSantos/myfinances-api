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
import com.lucasapds.finance.enums.LaunchType;
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

		Example<Launch> example = Example.of(launch,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return repository.findAll(example);
	}

	public void updateStatus(Launch launch, LaunchStatus status) {

		launch.setStatus(status);
		update(launch);

	}

	public void validate(Launch launch) {

		if (launch.getDescription() == null || launch.getDescription().trim().equals("")) {
			throw new RusinessRuleException("Informe uma descri????o v??lida");
		}

		if (launch.getMonth() == null || launch.getMonth() < 1 || launch.getMonth() > 12) {
			throw new RusinessRuleException("Informe um m??s v??lido");
		}
		if (launch.getYear() == null || launch.getYear().toString().length() != 4) {
			throw new RusinessRuleException("Informe um ano v??lido");
		}
		if (launch.getUser() == null || launch.getUser().getId() == null) {
			throw new RusinessRuleException("Informe um usu??rio");
		}
		if (launch.getValue() == null || launch.getValue().compareTo(BigDecimal.ZERO) < 1) {
			throw new RusinessRuleException("Informe um valor v??lido");
		}
		if (launch.getType() == null) {
			throw new RusinessRuleException("Informe um tipo de lan??amento");
		}
	}
	
	public Optional<Launch> getById(Long id){
		return repository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public BigDecimal getBanlanceByUser(Long id) {
		BigDecimal revenues = repository.getBalanceByLaunchTypeAndUser(id, LaunchType.REVENUE);
		BigDecimal expenditure = repository.getBalanceByLaunchTypeAndUser(id, LaunchType.EXPENSE);
		
		if(revenues == null) {
			revenues = BigDecimal.ZERO;
		}
		
		if(expenditure == null) {
			expenditure = BigDecimal.ZERO;
		}
		
		return revenues.subtract(expenditure);

}
}
