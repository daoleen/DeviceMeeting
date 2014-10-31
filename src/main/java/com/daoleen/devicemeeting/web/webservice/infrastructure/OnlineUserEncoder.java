package com.daoleen.devicemeeting.web.webservice.infrastructure;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class OnlineUserEncoder implements Encoder.Text<OnlineUser> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(OnlineUser user) throws EncodeException {
		return new Gson().toJson(user);
	}

}
