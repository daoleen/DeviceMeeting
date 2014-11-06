package com.daoleen.devicemeeting.web.webservice.infrastructure.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 11/7/14.
 */
public class OnlineUserList implements Serializable {
    private List<OnlineUser> onlineUsers;

    public OnlineUserList() {
        onlineUsers = new ArrayList<>();
    }

    public void addOnlineUser(OnlineUser user) {
        onlineUsers.add(user);
    }

    public List<OnlineUser> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<OnlineUser> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}
