package com.daoleen.devicemeeting.web.webservice.infrastructure.servicemessages;

import com.daoleen.devicemeeting.web.webservice.infrastructure.domain.OnlineUser;
import com.daoleen.devicemeeting.web.webservice.infrastructure.domain.OnlineUserList;
import com.daoleen.devicemeeting.web.webservice.infrastructure.domain.PointMessageBuffer;
import com.daoleen.devicemeeting.web.webservice.infrastructure.encoders.PointServiceMessageEncoder;

import javax.websocket.EncodeException;
import java.io.Serializable;

/**
 * Created by alex on 11/6/14.
 */
public class PointServiceMessage implements Serializable {
    public enum MessageType { points, onlineUser, onlineUserList, exception }
    private MessageType messageType;
    private PointMessageBuffer points;
    private OnlineUser onlineUser;
    private OnlineUserList onlineUserList;
    private String exception;

    public PointServiceMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public PointServiceMessage(MessageType messageType, Object object) {
        this(messageType);

        switch (messageType) {
            case points:
                this.points = (PointMessageBuffer) object;
                break;
            case onlineUser:
                this.onlineUser = (OnlineUser) object;
                break;
            case onlineUserList:
                this.onlineUserList = (OnlineUserList) object;
                break;
            default:
                this.exception = object.toString();
        }
    }

    @Override
    public String toString() {
        try {
            return new PointServiceMessageEncoder().encode(this);
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public PointMessageBuffer getPoints() {
        return points;
    }

    public void setPoints(PointMessageBuffer points) {
        this.points = points;
    }

    public OnlineUser getOnlineUser() {
        return onlineUser;
    }

    public void setOnlineUser(OnlineUser onlineUser) {
        this.onlineUser = onlineUser;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public OnlineUserList getOnlineUserList() {
        return onlineUserList;
    }

    public void setOnlineUserList(OnlineUserList onlineUserList) {
        this.onlineUserList = onlineUserList;
    }
}
