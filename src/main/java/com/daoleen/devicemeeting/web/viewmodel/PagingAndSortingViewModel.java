package com.daoleen.devicemeeting.web.viewmodel;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.daoleen.devicemeeting.web.enums.SortingFields;

public class PagingAndSortingViewModel<T> {
	private int pagesCount;
	private int currentPage;
	private int pageSize;
	private Direction direction;
	private SortingFields sortingFields;
	private List<T> items;
	
	public PagingAndSortingViewModel() {
		pagesCount = 1;
		currentPage = 1;
		pageSize = 10;
		direction = Direction.ASC;
		sortingFields = SortingFields.NAME;
	}
	
	public PagingAndSortingViewModel(int pagesCount, List<T> items) {
		this();
		this.pagesCount = pagesCount;
		this.items = items;
	}
	
	public PagingAndSortingViewModel(int pagesCount, int currentPage, List<T> items) {
		this(pagesCount, items);
		this.currentPage = currentPage;
	}
	
	public PagingAndSortingViewModel(int pagesCount, int currentPage,
			Direction direction, SortingFields sortingFields, List<T> items) {
		this(pagesCount, currentPage, items);
		this.direction = direction;
		this.sortingFields = sortingFields;
	}

	public int getPagesCount() {
		return pagesCount;
	}

	public void setPagesCount(int pagesCount) {
		this.pagesCount = pagesCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public SortingFields getSortingFields() {
		return sortingFields;
	}

	public void setSortingFields(SortingFields sortingFields) {
		this.sortingFields = sortingFields;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
