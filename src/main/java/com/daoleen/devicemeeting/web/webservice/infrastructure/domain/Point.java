package com.daoleen.devicemeeting.web.webservice.infrastructure.domain;

import java.io.Serializable;

public class Point implements Serializable {
	private static final long serialVersionUID = 1L;

	private double x;
	private double y;
	private String shape;
	private String color;
	
	public Point() {
	}

	public Point(double x, double y, String shape, String color) {
		super();
		this.x = x;
		this.y = y;
		this.shape = shape;
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return String.format("[%f; %f]: %s, %s", getX(), getY(), getShape(), getColor());
	}
}
