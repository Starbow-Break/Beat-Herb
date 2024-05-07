package store.beatherb.restapi.follow.follower.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.follow.domain.Follow;
import store.beatherb.restapi.member.domain.Member;

@Data
@Builder
public class RegistFollowerResponse {
    @NotNull(message = "팔로우 id는 null 값이 들어오면 안됩니다.")
    private Long id;

    @NotNull(message = "member id는 null 값이 들어오면 안됩니다.")
    private Long memberId;

    @NotNull(message = "내가 팔로우 하는 member id는 null 값이 들어오면 안됩니다.")
    private Long followMemberId;

    public static RegistFollowerResponse toDto(Follow follow){
        return RegistFollowerResponse.builder()
                .id(follow.getId())
                .memberId(follow.getMember().getId())
                .followMemberId(follow.getFollowMember().getId())
                .build();
    }
}
