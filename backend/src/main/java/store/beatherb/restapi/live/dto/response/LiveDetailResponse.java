package store.beatherb.restapi.live.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
public class LiveDetailResponse {
    private Long id;
    private String title;
    private List<LiveContentResponse> contentList;
    private MasterMemberResponse member;
    private String image;

    @Builder
    public LiveDetailResponse(Long id, String title, List<LiveContentResponse> contentList, MasterMemberResponse member){
        this.id = id;
        this.title = title;
        this.contentList = contentList;
        this.member = member;
        this.image = "/api/member/image/"+member.getId();
    }
}
