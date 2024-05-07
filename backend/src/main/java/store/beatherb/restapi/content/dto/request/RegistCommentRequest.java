package store.beatherb.restapi.content.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import store.beatherb.restapi.content.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Builder
@AllArgsConstructor
public class RegistCommentRequest {
    @NotNull(message = "contentId 가 필요합니다.")
    Long contentId;
    @NotNull(message = "body 가 필요합니다.")
    String body;
}
