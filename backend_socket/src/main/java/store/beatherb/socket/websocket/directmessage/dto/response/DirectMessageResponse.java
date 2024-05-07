package store.beatherb.socket.websocket.directmessage.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DirectMessageResponse {


    Long id;
    String type="DM";
    DMMember sender;
    DMMember receiver;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime createdAt;
    String message;
    @Data
    public static class DMMember{

        @Getter
        private Long id;
        private String nickname;
        private String picture;
    }

    @JsonSerialize
    public static class ReceiverExclusionMixin {
        @JsonIgnore
        private DMMember receiver;

        @JsonIgnore
        private Long id;
    }

}
