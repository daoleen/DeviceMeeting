package com.daoleen.devicemeeting.web.converter;

import com.daoleen.devicemeeting.web.enums.SortingFields;

public class RoomSortingFieldsConverter {

	public String convertToDatabaseColumn(SortingFields sortingField) {
		switch(sortingField) {
		case DATE:
			return "startingAt";
		case POPULARITY:
			return "peoplesCount";
		default:
			return "name";
		}
	}

	public SortingFields convertToEntityAttribute(String dbcolumnName) {
		switch (dbcolumnName) {
		case "startingAt":
			return SortingFields.DATE;
		case "peoplesCount":
			return SortingFields.POPULARITY;

		default:
			return SortingFields.NAME;
		}
	}

}
