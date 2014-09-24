package com.daoleen.devicemeeting.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.daoleen.devicemeeting.web.domain.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
}