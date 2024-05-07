package store.beatherb.restapi.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.member.domain.Member;

import java.util.List;

@Getter
public class MemberSearchResponse {

    Long id;
    String nickname;
    String image;

    @Builder
    private MemberSearchResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.image = "/api/member/image/"+id;
    }

    public static MemberSearchResponse toDto(Member member) {
        return MemberSearchResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
