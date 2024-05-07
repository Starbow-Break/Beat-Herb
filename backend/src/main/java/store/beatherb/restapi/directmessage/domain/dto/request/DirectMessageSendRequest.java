package store.beatherb.restapi.directmessage.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DirectMessageSendRequest {

    @NotNull(message = "수신자 id가 필요합니다.")
    Long receiverId;
    @NotNull(message = "DM 내용이 필요합니다.")
    String message;

//    public DirectMessageRequest(Long receiverId, String message) {
//        this.receiverId = receiverId;
//        this.message = message;
//        if (receiverId == null || message == null){
//            throw new D
//        }
//    }
}
