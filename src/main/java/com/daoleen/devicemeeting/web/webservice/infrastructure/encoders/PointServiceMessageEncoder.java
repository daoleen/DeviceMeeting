package com.daoleen.devicemeeting.web.webservice.infrastructure.encoders;

import com.daoleen.devicemeeting.web.webservice.infrastructure.servicemessages.PointServiceMessage;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by alex on 11/6/14.
 */
public class PointServiceMessageEncoder implements Encoder.Text<PointServiceMessage> {

    @Override
    public String encode(PointServiceMessage object) throws EncodeException {
        return new Gson().toJson(object);
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
