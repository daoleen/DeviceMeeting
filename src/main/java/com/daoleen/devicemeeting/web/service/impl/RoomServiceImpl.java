package com.daoleen.devicemeeting.web.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.daoleen.devicemeeting.web.domain.Room;
import com.daoleen.devicemeeting.web.repository.RoomRepository;
import com.daoleen.devicemeeting.web.service.RoomService;
import com.daoleen.devicemeeting.web.viewmodel.RoomPage;
import com.google.common.collect.Lists;

@Repository
@Transactional
public class RoomServiceImpl implements RoomService {
	
	private final static Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);
	
	@Autowired
	private RoomRepository roomRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly = true)
	public List<Room> findAll() {
		return Lists.newArrayList(roomRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Room findById(Long id) {
		return roomRepository.findOne(id); 
	}

	@Override
	public Room save(Room room) {
		return roomRepository.save(room);
	}

	@Transactional(readOnly = true)
	public List<Room> searchByName(String name) {
		return entityManager.createQuery("select r from Room r where r.name like %:name%", Room.class)
			.setParameter("name", name)
			.getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Room> searchByName(String keywords, RoomPage roomPage) {
		keywords = String.format("%%%s%%", keywords);
		Pageable pageable = roomPage.getPageable();
		String orderby = String.format("r.%s %s", pageable.getSort().iterator().next().getProperty(), pageable.getSort().iterator().next().getDirection().name());
		int start = pageable.getPageNumber()*pageable.getPageSize();
		logger.debug("Search by '{}', orderby: '{}', start position: {}, records count: {}", new Object[] { keywords, orderby, start, pageable.getPageSize() });
		
		
		// getting the total items
		long total = entityManager.createQuery("select count(r) from Room r where r.name like :name", Long.class)
			.setParameter("name", keywords)
			.getSingleResult();
		
		logger.debug("Total records count is: {}", total);
		
		if(total == 0) {
			return roomPage.createPage();
		}
		
		// searching
		// need to use few NamedQuery'es for one by one sorting type
		List<Room> rooms = entityManager.createQuery("select r from Room r where r.name like :name order by " + orderby, Room.class)
			.setParameter("name", keywords)
			.setFirstResult(start)
			.setMaxResults(pageable.getPageSize())
			.getResultList();
		
		return roomPage.createPage(Lists.newArrayList(rooms), total);
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public PagingAndSortingViewModel<Room> searchByName(String name, PagingAndSortingViewModel<Room> roomSortingModel) {
//		Pageable pageable = createPageable(roomSortingModel);
//		List<Room> items = entityManager.createQuery("select r from Room r where r.name like %:name% ORDER BY :orderby :orderDirection LIMIT :start,:end", Room.class)
//				.setParameter("name", name)
//				.setParameter("orderby", pageable.getSort().iterator().next().getProperty())
//				.setParameter("orderDirection", pageable.getSort().iterator().next().getDirection().name())
//				.setParameter("start", (pageable.getPageNumber()-1)*pageable.getPageSize())
//				.setParameter("end", pageable.getPageNumber()*pageable.getPageSize() + pageable.getPageSize())
//				.getResultList();
//		
//		roomSortingModel.setItems(items);
//		return roomSortingModel;
//	}
	@Override
	@Transactional(readOnly = true)
	public Page<Room> findAllPageable(Pageable pageable) {
		return roomRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Room> findAllPageable(RoomPage roomPage) {
		return findAllPageable(roomPage.getPageable());
	}
}
