package store.beatherb.restapi.follow.follower.dto.response;

import lombok.Builder;
import lombok.Data;
import store.beatherb.restapi.member.domain.Member;

@Data
public class FollowersResponse {
    private String name;
    private String image;
    private Long id;


    @Builder
    public FollowersResponse(Long id,String name) {
        this.id = id;
        this.name = name;
        this.image = "/api/member/image/"+id;
    }
}
