package com.daoleen.devicemeeting.web.webservice.infrastructure;

import java.io.Serializable;

public class OnlineUser implements Serializable {
	private static final long serialVersionUID = -7317709353894524233L;
	enum Status { conntected, disconnected };
	
	private long id;
	private String firstName;
	private String lastName;
	private String profileUrl;
	private String avatarUrl;
	private Status status;
	
	public OnlineUser(long id) {
		this(id, Status.conntected);
	}
	
	public OnlineUser(long id, Status status) {
		this.id = id;
		this.status = status;
	}

	public OnlineUser(long id, String firstName, String lastName,
			String profileUrl, String avatarUrl, Status status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileUrl = profileUrl;
		this.avatarUrl = avatarUrl;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
