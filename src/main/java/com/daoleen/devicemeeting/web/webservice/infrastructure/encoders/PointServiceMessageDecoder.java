package com.daoleen.devicemeeting.web.webservice.infrastructure.encoders;

import com.daoleen.devicemeeting.web.webservice.infrastructure.servicemessages.PointServiceMessage;
import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by alex on 11/6/14.
 */
public class PointServiceMessageDecoder implements Decoder.Text<PointServiceMessage> {
    @Override
    public PointServiceMessage decode(String s) throws DecodeException {
        return new Gson().fromJson(s, PointServiceMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
