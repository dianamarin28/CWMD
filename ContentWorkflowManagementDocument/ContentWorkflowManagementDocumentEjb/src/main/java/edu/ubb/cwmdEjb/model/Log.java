package edu.ubb.cwmdEjb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "log")
public class Log extends BaseEntity {

	private static final long serialVersionUID = -7402343709130286199L;

	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "logActionType", nullable = false)
	@Enumerated(EnumType.STRING)
	private LogActionType logActionType;

	public Log() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LogActionType getLogActionType() {
		return logActionType;
	}

	public void setLogActionType(LogActionType logActionType) {
		this.logActionType = logActionType;
	}

}
