package com.daoleen.devicemeeting.web.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.infrastructure.MyAuthenticationToken;
import com.daoleen.devicemeeting.web.webservice.infrastructure.OnlineUser;
import com.daoleen.devicemeeting.web.webservice.infrastructure.OnlineUserDecoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.OnlineUserEncoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.Point;
import com.daoleen.devicemeeting.web.webservice.infrastructure.PointDecoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.PointEncoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.PointMessageBuffer;

@ServerEndpoint(value = "/points", 
	encoders = { PointEncoder.class }, //OnlineUserEncoder.class }, 
	decoders = { PointDecoder.class } //,OnlineUserDecoder.class }
)
public class PointService {
	private final Logger logger = LoggerFactory.getLogger(PointService.class);
	//private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	// Map<RoomId, List<Clients>>
	private static Map<Long, List<Session>> peers = Collections.synchronizedMap(new HashMap<Long, List<Session>>());
	
	// сделать рефакторинг
	// все точки хранить на клиенте!!!
	//private static Map<Long, List<Point>> points = Collections.synchronizedMap(new HashMap<Long, List<Point>>(1));
	
	
	@OnOpen
	public void onOpen(Session peer) {
		if(logger.isDebugEnabled()) {
			logger.debug("Peer connected");
			peer.getRequestParameterMap().forEach((key, params) -> {
				logger.debug("Request key: {}", key);
				params.forEach(p -> logger.debug("\tRequest Key Value: {}", p));
			});
		}
		
		try {
			long roomId = getRoomIdFromPeer(peer);
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
		
		PointMessageBuffer message = new PointMessageBuffer();
		message.setPoints(new ArrayList<Point>());
		message.setRoomId(11111111111111l);
		broadcastMessage(message, peer);
	}
	
	@OnClose
	public void onClose(Session peer) {
		try {
			long roomId = getRoomIdFromPeer(peer);
			peers.remove(roomId, peer);
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			logger.error("Could not close connection for peer: the roomId parameter is empty");
			peer.getAsyncRemote().sendText("ERROR");
		}
	}
	
	@OnMessage
	public void receivePointMessageBuffer(PointMessageBuffer message, Session peer) {
		broadcastMessage(message, peer);
	}
	
//	@OnMessage
//	public void receiveOnlineUserMessage(OnlineUser message, Session peer){
//		broadcastMessage(message, peer);
//	}
	
	private long getRoomIdFromPeer(Session peer) {
		return Long.parseLong(peer.getRequestParameterMap().get("roomId").get(0));
	}
	
	private void broadcastMessage(Object message, Session fromPeer) {
		MyAuthenticationToken userToken = (MyAuthenticationToken) fromPeer.getUserPrincipal();
		User user = (User) userToken.getPrincipal();
		logger.debug("[{}] Message received: {}", user.getEmail(), message);
		
		try {
			long roomId = getRoomIdFromPeer(fromPeer);
			peers.get(roomId).parallelStream().forEach(p -> {
				if(!p.equals(fromPeer)) {
					// --------------------- For debug purposes only!!! --------------------------------//
					if(logger.isDebugEnabled()) {
						MyAuthenticationToken recepientToken = (MyAuthenticationToken) p.getUserPrincipal();
						User recepient = (User) recepientToken.getPrincipal();
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
}
