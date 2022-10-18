package com.chick.www.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyHandler extends TextWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(MyHandler.class);
	private List<WebSocketSession> users;
	private Map<String, Object> userMap;
	
	public MyHandler() {
		users  = new ArrayList<WebSocketSession>();
		userMap = new HashMap<String, Object>();
		
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("TextWebSocketHandler: 연결 생성, session :" + session);
		users.add(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("TextWebSocketHandler: 메세지수신,message:"+message.getPayload());
		
		String msg = (String) message.getPayload();
		JSONObject obj = new JSONObject(msg);
		
		Iterator it = obj.keys();
		while(it.hasNext()) {
			String key = (String)it.next();
			log.info("key:" + key + ",value:"+obj.getString(key));
		}
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
	}
}
