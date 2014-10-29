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
import com.daoleen.devicemeeting.web.webservice.infrastructure.PointDecoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.PointEncoder;
import com.daoleen.devicemeeting.web.webservice.infrastructure.PointMessageBuffer;

@ServerEndpoint(value = "/points", 
	encoders = { PointEncoder.class }, 
	decoders = { PointDecoder.class }
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
	public void receiveMessage(PointMessageBuffer message, Session peer) {
		MyAuthenticationToken userToken = (MyAuthenticationToken) peer.getUserPrincipal();
		User user = (User) userToken.getPrincipal();
		logger.debug("[{}] Message received: {}", user.getEmail(), message);
		
		try {
			long roomId = getRoomIdFromPeer(peer);
			peers.get(roomId).parallelStream().forEach(p -> {
				if(!p.equals(peer)) {
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
			peer.getAsyncRemote().sendText("ERROR");
		}
	}
	
	private long getRoomIdFromPeer(Session peer) {
		return Long.parseLong(peer.getRequestParameterMap().get("roomId").get(0));
	}
}
