package com.daoleen.devicemeeting.web.viewmodel;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.daoleen.devicemeeting.web.converter.RoomSortingFieldsConverter;
import com.daoleen.devicemeeting.web.domain.Room;
import com.daoleen.devicemeeting.web.enums.SortingFields;

public class RoomPage {
	private final static Logger logger = LoggerFactory.getLogger(RoomPage.class);
	private Pageable pageable;
	private RoomSortingFieldsConverter converter = new RoomSortingFieldsConverter();

	public RoomPage() {
		this(1);
	}
	
	public RoomPage(int page) {
		this(page, 10);
	}
	
	public RoomPage(int page, int pageSize) {
		this(page, pageSize, Direction.ASC, SortingFields.NAME);
	}
	
	public RoomPage(int page, int pageSize, Direction direction) {
		this(page, pageSize, direction, SortingFields.NAME);
	}

	public RoomPage(int page, int pageSize, Direction direction, SortingFields sortingField) {
		logger.debug("Page is: {}, PageSize is: {}, Direction is: {}, SortingField is: {}", page, pageSize, direction, sortingField);
		pageable = new PageRequest(page-1, pageSize, direction, converter.convertToDatabaseColumn(sortingField));
	}
	
	public Page<Room> createPage() {
		return createPage(new ArrayList<Room>(), 0);
	}
	
	public Page<Room> createPage(ArrayList<Room> items, long total) {
		return new PageImpl<Room>(items, pageable, total);
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
}
