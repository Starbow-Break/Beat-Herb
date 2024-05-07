package store.beatherb.restapi.follow.following.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingResponse {
    private String name;
    private String image;
    Long id;

    @Builder
    public FollowingResponse(String name , Long id) {
        this.id = id;
        this.name = name;
        this.image = "/api/member/image/"+id;
    }
}
