package store.beatherb.socket.infrastructure.kafka.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import store.beatherb.socket.websocket.directmessage.dto.response.DirectMessageResponse;
import store.beatherb.socket.websocket.util.WebSocketHandler;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final WebSocketHandler webSocketHandler;




    @KafkaListener(topics = "DM")
    public void consumeDM(String message) throws IOException{
        log.info("message : [{}]", message);

        //TODO : JSON 으로 컨버팅 후 receiver 보기
        ObjectMapper objectMapper = new ObjectMapper();
        DirectMessageResponse jsonToDirectMessage = objectMapper.readValue(message, DirectMessageResponse.class);



        WebSocketSession session = this.webSocketHandler.getSession(jsonToDirectMessage.getReceiver().getId());
        if(session != null){

            objectMapper.addMixIn(DirectMessageResponse.class, DirectMessageResponse.ReceiverExclusionMixin.class);
            String jsonString = objectMapper.writeValueAsString(jsonToDirectMessage);



            session.sendMessage(new TextMessage(jsonString));
        }


    }

}
