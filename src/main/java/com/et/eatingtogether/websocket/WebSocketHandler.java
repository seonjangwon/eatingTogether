package com.et.eatingtogether.websocket;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class WebSocketHandler extends TextWebSocketHandler {

    Map<String, WebSocketSession> sessionMap = new HashMap<>();

    // connection established
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        if (userId!=null) {
            System.out.println("userId = " + userId+" 연결됨 ");
            sessionMap.put(userId,session);
        }
    }

    // message
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = getUserId(session);

        String[] msg = message.getPayload().split(",");
        if (msg != null){
            String target = msg[0];
            String type = msg[1]; // 1은 주문 2는 배송 출발
            WebSocketSession targetSession = sessionMap.get(target);
            if (type.equals("1")) {
                if (targetSession != null) { // 실시간 접속시
                    TextMessage textMessage = new TextMessage(userId + "님의 주문이 들어왔습니다");
                    targetSession.sendMessage(textMessage);
                }
            } else if (type.equals("2")){
                if (targetSession != null) { // 실시간 접속시
                    TextMessage textMessage = new TextMessage(userId + "님의 주문이 배송을 출발했습니다");
                    targetSession.sendMessage(textMessage);
                }
            }
        }
    }

    // connection closed
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserId(session);
        if (userId!=null){
            System.out.println(userId + " 연결 종료됨");
            sessionMap.remove(userId);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println(session.getId() + " 익셉션 발생: " + exception.getMessage());

    }

    public String getUserId(WebSocketSession session) {
        Map<String, Object> httpSession = session.getAttributes();
        String customer = (String) httpSession.get("customerLoginEmail");
        String store = (String) httpSession.get("storeLoginEmail");

        if (customer == null) {
            return store;
        } else {
            return customer;
        }

    }
}
