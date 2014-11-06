package com.daoleen.devicemeeting.web.domain;

import com.daoleen.devicemeeting.web.converter.LocalDateTimePersistenceConverter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "invites")
public class Invite implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@Length(max = 255)
	@Column(name = "comment", length = 255)
	private String comment;
	
	@NotNull
	@Column(name = "is_accepted")
	private boolean accepted;
	
	@NotNull
	@Column(name = "is_rejected")
	private boolean rejected;
	
	@Column(name = "date")
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalDateTime date;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inviter_id")
	private User inviter;

	
	// Generated code
	
	public Invite() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public boolean isAccepted() {
		return accepted;
	}



	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}



	public boolean isRejected() {
		return rejected;
	}



	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}



	public LocalDateTime getDate() {
		return date;
	}



	public void setDate(LocalDateTime date) {
		this.date = date;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Room getRoom() {
		return room;
	}



	public void setRoom(Room room) {
		this.room = room;
	}



	public User getInviter() {
		return inviter;
	}



	public void setInviter(User inviter) {
		this.inviter = inviter;
	}
	
	
}
