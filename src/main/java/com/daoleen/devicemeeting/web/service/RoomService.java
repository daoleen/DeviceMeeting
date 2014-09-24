package com.daoleen.devicemeeting.web.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daoleen.devicemeeting.web.domain.Room;
import com.daoleen.devicemeeting.web.viewmodel.PagingAndSortingViewModel;
import com.daoleen.devicemeeting.web.viewmodel.RoomPage;

public interface RoomService {
	public List<Room> findAll();
	public Room findById(Long id);
	public Room save(Room room);
	public Page<Room> searchByName(String keywords, RoomPage roomPage);
	public Page<Room> findAllPageable(Pageable pageable);
	public Page<Room> findAllPageable(RoomPage roomPage);
}