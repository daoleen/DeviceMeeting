package com.daoleen.devicemeeting.web.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.daoleen.devicemeeting.web.enums.SortingFields;
import com.daoleen.devicemeeting.web.viewmodel.RoomPage;

public class RoomPageWebArgumentsResolver implements WebArgumentResolver {
	
	private final static Logger logger = LoggerFactory.getLogger(RoomPageWebArgumentsResolver.class);

	@Override
	public Object resolveArgument(MethodParameter param, NativeWebRequest nativeRequest)
			throws Exception {
		
		RoomPageParameter roomPageParameter = param.getParameterAnnotation(RoomPageParameter.class);
		
		if(roomPageParameter != null) {
			Integer page = null;
			Integer pageSize = null;
			Direction direction = null;
			SortingFields sortingField = null;
			
			try {
				page = Integer.parseInt(nativeRequest.getParameter("page"));
			}
			catch(NumberFormatException e) {
				logger.debug("The NumberFormatException was occured when one parses the page parameter");
				return new RoomPage();
			}
			
			try {
				pageSize = Integer.parseInt(nativeRequest.getParameter("pageSize"));
			}
			catch(NumberFormatException e) {
				logger.debug("The NumberFormatException was occured when one parses the pageSize parameter");
				return new RoomPage(page);
			}
			
			try {
				direction = Direction.valueOf(nativeRequest.getParameter("direction"));
			}
			catch(NullPointerException e) {
				logger.debug("The NullPointerException was occured when one parses the direction parameter");
				return new RoomPage(page, pageSize);
			}
			
			try {
				sortingField = SortingFields.valueOf(nativeRequest.getParameter("sortingField"));
			}
			catch(NullPointerException e) {
				logger.debug("The NullPointerException was occured when one parses the sortingField parameter");
				return new RoomPage(page, pageSize, direction);
			}
			
			return new RoomPage(page, pageSize, direction, sortingField);
		}
		
		return UNRESOLVED;
	}

}
