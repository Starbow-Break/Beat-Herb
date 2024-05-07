package store.beatherb.socket.websocket.util;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {



    private static final ConcurrentHashMap<Long, WebSocketSession> CLIENTS = new ConcurrentHashMap<Long, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        long sessionId = (long)session.getAttributes().get("id");
        WebSocketSession prevSession = CLIENTS.get(sessionId);
        if(prevSession != null){
            prevSession.close();
        }
        CLIENTS.put((long)session.getAttributes().get("id"),session);
//        CLIENTS.put("1", session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove((long)session.getAttributes().get("id"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();  //메시지를 보낸 아이디
//        System.out.println(message);
    }

    public WebSocketSession getSession(long sessionId) {
        return CLIENTS.get(sessionId);
    }
}