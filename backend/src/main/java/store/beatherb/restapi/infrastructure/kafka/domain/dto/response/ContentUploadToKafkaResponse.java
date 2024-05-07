package store.beatherb.restapi.infrastructure.kafka.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.directmessage.domain.DirectMessage;
import store.beatherb.restapi.member.domain.Member;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@Builder
public class ContentUploadToKafkaResponse {

    Long writerId;
    String fileName;

}
