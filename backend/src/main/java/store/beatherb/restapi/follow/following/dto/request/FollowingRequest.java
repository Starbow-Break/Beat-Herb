package store.beatherb.restapi.follow.following.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FollowingRequest {
    @NotNull(message = "FollowMemberId는 null 값이 들어오면 안됩니다.")
    private Long followMemberId;
}
