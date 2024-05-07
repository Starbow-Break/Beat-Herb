package store.beatherb.restapi.live.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.live.domain.Guest;
import store.beatherb.restapi.live.domain.Live;
import store.beatherb.restapi.live.domain.LiveContent;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.openvidu.dto.response.OpenViduJoinSessionResponse;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class LiveJoinResponse {

    Long id;
    String role;
    String token;
    String title;
    String describe;

    List<LiveJoinResponseContent> contentList ;
    List<LiveJoinResponseGuest> guestList;

    public static  LiveJoinResponse toDto(Live live, OpenViduJoinSessionResponse openViduJoinSessionResponse,String role){
        Long id = live.getId();
        String token = openViduJoinSessionResponse.getToken();
        String title = live.getTitle();
        String describe = live.getDescribe();
        List<LiveContent> liveContentList = live.getLiveContentList();
        List<LiveJoinResponseContent> contentList = new ArrayList<>();
        List<Guest> guestEntityList = live.getGuestList();
        List<LiveJoinResponseGuest> guestList = new ArrayList<>();
        for(LiveContent liveContent : liveContentList){
            contentList.add(
                    LiveJoinResponseContent.toDto(liveContent.getContent())
            );
        }
        for(Guest g: guestEntityList){
            guestList.add(
                    LiveJoinResponseGuest.toDto(g.getMember())
            );

        }
        return LiveJoinResponse.builder()
                .id(id)
                .token(token)
                .role(role)
                .title(title)
                .describe(describe)
                .contentList(contentList)
                .guestList(guestList)
                .build();

    }

    @Getter
    private static class LiveJoinResponseContent{
        Long id;
        String title;

        @Builder
        private LiveJoinResponseContent(Long id, String title) {
            this.id = id;
            this.title = title;
        }

        public static LiveJoinResponseContent toDto(Content entity){
            return LiveJoinResponseContent.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .build();
        }
    }

    @Getter
    private static class LiveJoinResponseGuest{
        Long id;
        String nickname;


        @Builder
        private LiveJoinResponseGuest(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        public static LiveJoinResponseGuest toDto(Member member){
            return LiveJoinResponseGuest.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build();
        }
    }



}
