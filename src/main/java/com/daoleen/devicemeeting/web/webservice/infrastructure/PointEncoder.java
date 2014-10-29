package com.daoleen.devicemeeting.web.webservice.infrastructure;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class PointEncoder implements Encoder.Text<PointMessageBuffer> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(PointMessageBuffer object) throws EncodeException {
		return new Gson().toJson(object);
	}

}
