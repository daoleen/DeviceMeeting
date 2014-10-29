package com.daoleen.devicemeeting.web.webservice.infrastructure;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class PointDecoder implements Decoder.Text<PointMessageBuffer> {

	@Override
	public void init(EndpointConfig config) {	
	}

	@Override
	public void destroy() {
	}

	@Override
	public PointMessageBuffer decode(String s) throws DecodeException {
		return new Gson().fromJson(s, PointMessageBuffer.class);
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
