package com.daoleen.devicemeeting.web.webservice.infrastructure.domain;

import java.io.Serializable;

public class OnlineUser implements Serializable {
	private static final long serialVersionUID = -7317709353894524233L;
	public enum Status {connected, disconnected }
	
	private long id;
	private String username;
	private String profileUrl;
	private String avatarUrl;
	private Status status;
	
	public OnlineUser(long id) {
		this(id, Status.connected);
	}
	
	public OnlineUser(long id, Status status) {
		this.id = id;
		this.status = status;
	}

	public OnlineUser(long id, String username, String profileUrl,
					  String avatarUrl, Status status) {
		super();
		this.id = id;
		this.username = username;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
