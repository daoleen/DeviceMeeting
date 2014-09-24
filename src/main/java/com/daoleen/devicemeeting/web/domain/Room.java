package com.daoleen.devicemeeting.web.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.daoleen.devicemeeting.web.converter.LocalDateTimePersistenceConverter;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@Length(min = 3, max = 255)
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Length(max = 1024)
	@Column(name = "description", length = 1024)
	private String description;
	
//	Need no map foreign key
//	@Column(name = "owner_id")
//	private Long ownerId;
	
	@Column(name = "is_anyone_speaker", nullable = false)
	private boolean anyoneSpeaker;
	
	@Column(name = "is_public", nullable = false)
	private boolean publicRoom;
	
	@Length(max = 255)
	@Column(name = "logo", length = 255)
	private String logo;
	
	@Column(name = "starting_at", nullable = false)
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalDateTime startingAt;
	
	@Min(0)
	@Column(name = "peoples_count", nullable = false)
	private int peoplesCount;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "owner_id")
	private User owner;

	
	// Generated code

	@Transient
	private static final long serialVersionUID = 7144563251323409738L;
	
	public Room() {
		startingAt = LocalDateTime.now();
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




//	public Long getOwnerId() {
//		return ownerId;
//	}
//
//
//
//
//	public void setOwnerId(Long ownerId) {
//		this.ownerId = ownerId;
//	}




	public boolean isAnyoneSpeaker() {
		return anyoneSpeaker;
	}




	public void setAnyoneSpeaker(boolean anyoneSpeaker) {
		this.anyoneSpeaker = anyoneSpeaker;
	}




	public boolean isPublicRoom() {
		return publicRoom;
	}




	public void setPublicRoom(boolean publicRoom) {
		this.publicRoom = publicRoom;
	}




	public String getLogo() {
		return logo;
	}




	public void setLogo(String logo) {
		this.logo = logo;
	}




	public User getOwner() {
		return owner;
	}




	public void setOwner(User owner) {
		this.owner = owner;
	}




	public LocalDateTime getStartingAt() {
		return startingAt;
	}




	public void setStartingAt(LocalDateTime startingAt) {
		this.startingAt = startingAt;
	}




	public int getPeoplesCount() {
		return peoplesCount;
	}




	public void setPeoplesCount(int peoplesCount) {
		this.peoplesCount = peoplesCount;
	}
	
	
}
