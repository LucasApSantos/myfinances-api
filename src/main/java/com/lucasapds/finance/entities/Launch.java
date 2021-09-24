package com.lucasapds.finance.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

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





@Entity
@Table(name = "launch", schema = "finances")
public class Launch implements Serializable{
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
	
	@Column(name = "value")
	private BigDecimal value;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "data_Registration")
	private Instant dataRegistration;
	
	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private LaunchType type;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private  LaunchStatus status;
	
	public Launch() {
		
	}
	
	/*public Launch(Long id, String description, Integer month, Integer year, BigDecimal value, User user, LaunchType type, LaunchStatus status, Instant dataRegistration) {
		
		this.id = id;
		this.description = description;
		this.month = month;
		this.year = year;
		this.value = value;
		this.user = user;
		this.type = type;
		this.status = status;
		this.dataRegistration = dataRegistration;	
			
		
	}
*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	public Instant getDataRegistration() {
		return dataRegistration;
	}

	public void setDataRegistration(Instant dataRegistration) {
		this.dataRegistration = dataRegistration;
	}

	public LaunchType getType() {
		return type;
	}

	public void setType(LaunchType type) {
		this.type = type;
		
	}

	public LaunchStatus getStatus() {
		return status;
	}

	public void setStatus(LaunchStatus status) {
		this.status = status;
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Launch other = (Launch) obj;
		return Objects.equals(id, other.id);
	}

	
	
	
}
