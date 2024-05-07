package store.beatherb.restapi.follow.follower.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegistFollowerRequest {
    @NotNull(message = "팔로우 할 member id는 null 값이 들어오면 안됨.")
    private Long id;
}
