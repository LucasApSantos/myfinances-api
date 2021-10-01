package com.lucasapds.finance.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasapds.finance.entities.Launch;
import com.lucasapds.finance.enums.LaunchType;

public interface LaunchRepository extends JpaRepository<Launch, Long>{

	@Query( value = " select sum(l.value) from Launch l join l.user u where u.id = :idUser and l.type =:type group by u")
	BigDecimal getBalanceByLaunchTypeAndUser(@Param("idUser") Long idUser, @Param("type") LaunchType type);
}
