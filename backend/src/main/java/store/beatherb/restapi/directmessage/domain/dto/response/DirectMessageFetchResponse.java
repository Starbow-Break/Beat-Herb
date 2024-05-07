package store.beatherb.restapi.directmessage.domain.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.directmessage.domain.DirectMessage;
import store.beatherb.restapi.member.domain.Member;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class DirectMessageFetchResponse {


    DirectMessageFetchMember sender;
    DirectMessageFetchMember receiver;
    String message;
    LocalDateTime createdAt;

    @Builder
    @Data
    public static class DirectMessageFetchMember {
        Long id;
        String nickname;
    }

    public static DirectMessageFetchResponse toDto(DirectMessage directMessage) {

        Member sender = directMessage.getSender();
        Member receiver = directMessage.getReceiver();
        String message = directMessage.getMessage();
        LocalDateTime createdAt = directMessage.getCreatedAt();

        DirectMessageFetchMember senderDto = DirectMessageFetchMember.builder()
                .id(sender.getId())
                .nickname(sender.getNickname())
                .build();

        DirectMessageFetchMember receiverDto = DirectMessageFetchMember.builder()
                .id(receiver.getId())
                .nickname(receiver.getNickname())
                .build();

        return  DirectMessageFetchResponse.builder()
                .createdAt(createdAt)
                .message(message)
                .sender(senderDto)
                .receiver(receiverDto)
                .build();

    }
}
