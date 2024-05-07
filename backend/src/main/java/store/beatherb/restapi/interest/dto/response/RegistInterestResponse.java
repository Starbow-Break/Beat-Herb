package store.beatherb.restapi.interest.dto.response;

import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.dto.MemberDTO;

@Data
@Builder
public class RegistInterestResponse {
    private Long id;
    private HashTag hashTag;
    private Member member;

    public static RegistInterestResponse toDTO(Interest interest) {
        return RegistInterestResponse.builder()
                .id(interest.getId())
                .hashTag(interest.getHashTag())
                .member(interest.getMember())
                .build();
    }
}
