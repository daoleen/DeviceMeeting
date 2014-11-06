package com.daoleen.devicemeeting.web.webservice.infrastructure.domain;

import java.io.Serializable;
import java.util.List;

public class PointMessageBuffer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long roomId;
	private List<Point> points;
	
	public PointMessageBuffer() {
	}

	public PointMessageBuffer(long roomId, List<Point> points) {
		super();
		this.roomId = roomId;
		this.points = points;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public final int getCapacity() {
		return points.size();
	}
	
	
	public String toString() {
		return String.format("Room %d has %d points int buffer", getRoomId(), getCapacity());
	}
}
