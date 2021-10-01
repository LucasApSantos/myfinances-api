package com.lucasapds.finance.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasapds.finance.enums.LaunchStatus;
import com.lucasapds.finance.enums.LaunchType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "launch", schema = "finances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Launch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	@Column(name = "description")
	private String description;

	@Column(name = "month")
	private Integer month;

	@Column(name = "year")
	private Integer year;

	


	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	
	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "data_Registration")
	private Instant dataRegistration;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private LaunchType type;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private LaunchStatus status;

}
