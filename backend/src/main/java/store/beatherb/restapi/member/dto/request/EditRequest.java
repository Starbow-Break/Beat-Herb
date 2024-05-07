package store.beatherb.restapi.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import store.beatherb.restapi.interest.dto.request.PutInterestRequest;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class EditRequest {
    String nickname;
    Boolean dmAgree;
    // 향후 파일 업로드 로직 확인하고 Service Layer 및 Entity에 반영 필요
    MultipartFile picture;
    List<Long> hashTagIdList;
}