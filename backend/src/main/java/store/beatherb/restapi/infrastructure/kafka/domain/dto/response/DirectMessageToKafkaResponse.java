package store.beatherb.restapi.infrastructure.kafka.domain.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.directmessage.domain.DirectMessage;
import store.beatherb.restapi.member.domain.Member;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class DirectMessageToKafkaResponse {

    @Builder
    public DirectMessageToKafkaResponse(Long id, Member sender, Member receiver, LocalDateTime createdAt, String message) {
        this.id = id;
        this.sender =
                DMMember.builder().id(sender.getId())
                        .nickname(sender.getNickname())
                        .build();

        this.receiver = DMMember.builder().id(receiver.getId())
                .nickname(receiver.getNickname())
                .build();;
        this.createdAt = createdAt;
        this.message = message;
    }

    public static DirectMessageToKafkaResponse toDTO(DirectMessage directMessage){
        return DirectMessageToKafkaResponse.builder()
                .id(directMessage.getId())
                .sender(directMessage.getSender())
                .receiver(directMessage.getReceiver())
                .createdAt(directMessage.getCreatedAt())
                .message(directMessage.getMessage())
                .build();
    }

    Long id;
    DMMember sender;
    DMMember receiver;



    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime createdAt;
    String message;


    @Builder
    @AllArgsConstructor
    @Getter
    static class DMMember{

        private Long id;
        private String nickname;
        private String picture;
    }

}
