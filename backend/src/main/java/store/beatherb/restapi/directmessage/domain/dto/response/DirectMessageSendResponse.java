package store.beatherb.restapi.directmessage.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class DirectMessageSendResponse {
    LocalDateTime createdAt;
}
