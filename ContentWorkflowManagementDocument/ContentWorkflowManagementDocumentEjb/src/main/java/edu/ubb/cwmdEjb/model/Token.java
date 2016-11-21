package edu.ubb.cwmdEjb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token extends BaseEntity {

	private static final long serialVersionUID = -6804065562614777880L;

	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;

	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "creationDate", nullable = false)
	private LocalDate creationDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

}
