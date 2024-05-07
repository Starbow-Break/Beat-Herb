package store.beatherb.restapi.follow.follower.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeleteFollowerRequest {
    @NotNull(message = "팔로워 id는 null 값이 들어오면 안됩니다.")
    private Long id;
}
