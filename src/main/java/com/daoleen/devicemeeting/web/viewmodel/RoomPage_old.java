package com.daoleen.devicemeeting.web.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.daoleen.devicemeeting.web.domain.Room;
import com.daoleen.devicemeeting.web.enums.SortingFields;

public class RoomPage_old extends PageImpl<Room> {
	
	private static final long serialVersionUID = -2003723198241755458L;

	public RoomPage_old() {
		this(1);
	}
	
	public RoomPage_old(int page) {
		this(page, 10);
	}
	
	public RoomPage_old(int page, int pageSize) {
		this(page, pageSize, Direction.ASC, SortingFields.NAME);
	}
	
	public RoomPage_old(int page, int pageSize, Direction direction) {
		this(page, pageSize, direction, SortingFields.NAME);
	}

	public RoomPage_old(int page, int pageSize, Direction direction, SortingFields sortingField) {
		this(new ArrayList<Room>(), new PageRequest(page, pageSize, direction, sortingField.name()), 0);
	}
	
	public RoomPage_old(List<Room> content) {
		super(content);
	}

	public RoomPage_old(List<Room> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}
}
