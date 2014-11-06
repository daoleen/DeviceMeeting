package com.daoleen.devicemeeting.web.webservice;

import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.infrastructure.MyAuthenticationToken;
import com.daoleen.devicemeeting.web.webservice.infrastructure.domain.OnlineUser;
import com.daoleen.devicemeeting.web.webservice.infrastructure.domain.OnlineUserList;
import com.daoleen.devicemeeting.web.webservice.infrastructure.encoders.PointServiceMessageDecoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.encoders.PointServiceMessageEncoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.servicemessages.PointServiceMessage;
import org.hibernate.annotations.common.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint(value = "/points", 
	encoders = {PointServiceMessageEncoder.class},
	decoders = {PointServiceMessageDecoder.class}
)
public class PointService {
	private final Logger logger = LoggerFactory.getLogger(PointService.class);
	private final static String urlFirstPath = "/DeviceMeeting";
	private final static String userDirAvatars="/resources/uploads/avatars/";

	// Map<RoomId, List<Clients>>
	private static Map<Long, List<Session>> peers = Collections.synchronizedMap(new HashMap<Long, List<Session>>());
	
	// сделать рефакторинг
	// все точки хранить на клиенте!!!
	//private static Map<Long, List<Point>> points = Collections.synchronizedMap(new HashMap<Long, List<Point>>(1));

	@OnOpen
	public void onOpen(Session peer) {
		long roomId = 0;
		logger.debug("Peer connected");
		if(logger.isDebugEnabled()) {
			peer.getRequestParameterMap().forEach((key, params) -> {
				logger.debug("Request key: {}", key);
				params.forEach(p -> logger.debug("\tRequest Key Value: {}", p));
			});
		}
		
		try {
			roomId = getRoomIdFromPeer(peer);
			List<Session> peerSessions = peers.get(roomId);
			if(peerSessions == null){
				peerSessions = new ArrayList<Session>();
				peerSessions.add(peer);
				peers.put(roomId, peerSessions);
			} else {
				peerSessions.add(peer);
			}
		}
		catch(NumberFormatException | IndexOutOfBoundsException e) {
			logger.error("Could not connect peer to service: the roomId parameter is empty");
			peer.getAsyncRemote().sendText("ERROR");
		}

		// Notify all connected users about new user
		User connectedUser = getUserFromPeer(peer);
		PointServiceMessage message = new PointServiceMessage(PointServiceMessage.MessageType.onlineUser,
				createOnlineUser(connectedUser, OnlineUser.Status.connected)
		);
		broadcastMessage(message, peer);

		// Send a list of connected users to peer
		PointServiceMessage msg = new PointServiceMessage(PointServiceMessage.MessageType.onlineUserList,
				createOnlineUserList(roomId, peer)
		);
		peer.getAsyncRemote().sendObject(msg);
	}
	
	@OnClose
	public void onClose(Session peer) {
		logger.debug("User has leave the service");

		User disconnectedUser = getUserFromPeer(peer);
		PointServiceMessage message = new PointServiceMessage(PointServiceMessage.MessageType.onlineUser,
				new OnlineUser(disconnectedUser.getId(), OnlineUser.Status.disconnected)
		);

		broadcastMessage(message, peer);

		try {
			long roomId = getRoomIdFromPeer(peer);
			peers.remove(roomId, peer);
			logger.debug("Count of joined users: {}", peers.get(roomId).size());
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			logger.error("Could not close connection for peer: the roomId parameter is empty");
			peer.getAsyncRemote().sendText("ERROR: Could not close connection for peer: the roomId parameter is empty");
		} catch(Exception e) {
			logger.error("An unknown exception has been occured.");
			logger.error(e.getStackTrace().toString());
		}
	}

	@OnMessage
	public void onMessage(PointServiceMessage message, Session peer) {
		broadcastMessage(message, peer);
	}


	private void broadcastMessage(Object message, Session fromPeer) {
		User user = getUserFromPeer(fromPeer);
		logger.debug("[{}] Message received: {}", user.getEmail(), message);
		
		try {
			long roomId = getRoomIdFromPeer(fromPeer);
			peers.get(roomId).parallelStream().forEach(p -> {
				if(!p.equals(fromPeer)) {
					// --------------------- For debug purposes only!!! --------------------------------//
					if(logger.isDebugEnabled()) {
						User recepient = getUserFromPeer(p);
						logger.debug("Send message from [{}] to [{}]", user.getEmail(), recepient.getEmail());
					}
					
					try {
						logger.debug("Message: " + message);
						p.getAsyncRemote().sendObject(message);
					} catch (Exception e) {
						logger.error("An exception was occured while sending message to a client");
						e.printStackTrace();
					}
				}
			});
		}
		catch(NumberFormatException | IndexOutOfBoundsException e) {
			logger.error("Could not receive a message from peer: the roomId parameter is empty");
			fromPeer.getAsyncRemote().sendText("ERROR");
		}
	}


	private User getUserFromPeer(Session peer) {
		MyAuthenticationToken recepientToken = (MyAuthenticationToken) peer.getUserPrincipal();
		return (User) recepientToken.getPrincipal();
	}

	private long getRoomIdFromPeer(Session peer) {
		return Long.parseLong(peer.getRequestParameterMap().get("roomId").get(0));
	}

	private OnlineUser createOnlineUser(User user, OnlineUser.Status status) {
		return new OnlineUser(
				user.getId(),
				user.toString(),
				String.format("%s/account/%s", urlFirstPath, user.getId()),
				String.format("%s/%s", urlFirstPath,
						StringHelper.isEmpty(user.getAvatar())
								? "/resources/images/userAvatar.png"
								: userDirAvatars.concat(user.getAvatar())
				),
				status
		);
	}

	private OnlineUserList createOnlineUserList(long roomId, Session peer) {
		OnlineUserList list = new OnlineUserList();
		peers.get(roomId).parallelStream().forEach(
				s -> {
					if(!s.equals(peer)) {
						list.addOnlineUser(
								createOnlineUser(getUserFromPeer(s), OnlineUser.Status.connected)
						);
					}
				}
		);
		return list;
	}
}
