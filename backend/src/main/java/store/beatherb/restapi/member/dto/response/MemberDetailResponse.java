package store.beatherb.restapi.member.dto.response;


import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.Creator;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.content.domain.embed.ContentTypeEnum;
import store.beatherb.restapi.content.dto.HashTagDTO;
import store.beatherb.restapi.follow.domain.Follow;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.live.domain.Guest;
import store.beatherb.restapi.live.domain.Live;
import store.beatherb.restapi.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MemberDetailResponse {

    Long id;
    String nickname;
    //Live 여부
    //Guest 목록

    LiveDTO live;
    List<LiveDTO> guestList;
    List<ContentDTO> vocalList;
    List<ContentDTO> melodyList;
    List<ContentDTO> soundTrackList;
    List<HashTagDTO> hashTagList;
    List<FollowerDTO> followerList;
    List<FollowingDTO> followingList;
    String image;
    boolean follow;

    @Builder
    private MemberDetailResponse(Long id, String nickname, LiveDTO live, List<LiveDTO> guestList, List<ContentDTO> vocalList, List<ContentDTO> melodyList, List<ContentDTO> soundTrackList, List<HashTagDTO> hashTagList, List<FollowerDTO> followerList, List<FollowingDTO> followingList,
                                 boolean follow) {
        this.id = id;
        this.nickname = nickname;
        this.live = live;
        this.guestList = guestList;
        this.vocalList = vocalList;
        this.melodyList = melodyList;
        this.soundTrackList = soundTrackList;
        this.image = "/api/member/image/"+id;
        this.hashTagList = hashTagList;
        this.followerList = followerList;
        this.followingList = followingList;
        this.follow = follow;
    }
    public static MemberDetailResponse toDto(Member entity,boolean follow){
        Long id = entity.getId();
        String nickname = entity.getNickname();
        Live live = entity.getLive();
        List<Creator>  creatorList  = entity.getCreatorList();
        List<Guest> liveGuestList = entity.getLiveGuestList();


        List<Interest> interestList = entity.getInterestList();

        List<Follow> followerList = entity.getFollowerList();
        List<Follow> followingList = entity.getFollowingList();

        List<ContentDTO> vocalList = new ArrayList<>();
        List<ContentDTO> melodyList = new ArrayList<>();
        List<ContentDTO> soundTrackList = new ArrayList<>();
        List<LiveDTO> guestList = new ArrayList<>();
        for(Creator c: creatorList){
            Content content = c.getContent();
            ContentDTO contentDTO = ContentDTO.toDto(content);
            ContentTypeEnum type = c.getContent().getContentType().getType();
            if(type == ContentTypeEnum.VOCAL){
                vocalList.add(contentDTO);
            }
            else if(type ==ContentTypeEnum.MELODY){
                melodyList.add(contentDTO);
            }
            else{
                soundTrackList.add(contentDTO);
            }
        }
        LiveDTO liveDTO = LiveDTO.toDto(live);

        for(Guest g: liveGuestList){
            Live l = g.getLive();
            guestList.add(LiveDTO.toDto(l));
        }

        List<FollowerDTO> followerDTOList = new ArrayList<>();
        for(Follow follower : followerList){
            followerDTOList.add(FollowerDTO.toDto(follower));
        }

        List<FollowingDTO> followingDTOlist = new ArrayList<>();
        for(Follow following : followingList){
            followingDTOlist.add(FollowingDTO.toDto(following));
        }

        List<HashTagDTO> hashTagList = new ArrayList<>();
        for(Interest interest : interestList){
            HashTag hashTag = interest.getHashTag();
            hashTagList.add(HashTagDTO.toDto(hashTag));
        }

        return MemberDetailResponse.builder()
                .id(id)
                .nickname(nickname)
                .live(liveDTO)
                .guestList(guestList)
                .hashTagList(hashTagList)
                .vocalList(vocalList)
                .melodyList(melodyList)
                .soundTrackList(soundTrackList)
                .followerList(followerDTOList)
                .followingList(followingDTOlist)
                .follow(follow)
                .build();

    }



    //vocalList
    //melodyList
    //soundTrackList
    //interest


    @Getter
    private static class ContentDTO {
        Long id;
        String title;
        String image;
        List<CreatorDTO> creatorList;
        String type;

        @Builder
        private ContentDTO(Long id, String title, List<CreatorDTO> creatorList,String type) {
            this.id = id;
            this.title = title;
            this.image = "/api/content/image/"+id;
            this.creatorList = creatorList;
            this.type = type;
        }

        public static ContentDTO toDto(Content entity){
            Long id = entity.getId();
            String type = entity.getContentType().getType().name();
            List<CreatorDTO> creatorList = new ArrayList<>();
            String title = entity.getTitle();
            for(Creator creator : entity.getCreatorList()){
                CreatorDTO creatorDTO = CreatorDTO.toDto(creator.getCreator());
                creatorList.add(creatorDTO);
            }
            return ContentDTO.builder()
                    .id(id)
                    .title(title)
                    .type(type)
                    .creatorList(creatorList)
                    .build();

        }


        @Getter
        private static class CreatorDTO {
            Long id;
            String nickname;

            @Builder
            private CreatorDTO(Long id, String nickname) {
                this.id = id;
                this.nickname = nickname;
            }
            public static CreatorDTO toDto(Member entity){
                Long id = entity.getId();
                String nickname = entity.getNickname();
                return CreatorDTO.builder()
                        .id(id)
                        .nickname(nickname)
                        .build();
            }
        }
    }

    @Getter
    private static class LiveDTO {
        Long id;
        String title;
        String describe;

        @Builder
        private LiveDTO(Long id, String title, String describe) {
            this.id = id;
            this.title = title;
            this.describe = describe;
        }

        public static LiveDTO toDto(Live entity) {
            if(entity == null){
                return  null;
            }
            Long id = entity.getId();
            String title = entity.getTitle();
            String describe = entity.getDescribe();
            return LiveDTO.builder()
                    .id(id)
                    .title(title)
                    .describe(describe)
                    .build();
        }
    }

    @Getter
    private static class FollowerDTO {
        Long id;
        String nickname;
        String image;

        @Builder
        private FollowerDTO(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
            this.image = "/api/member/image/"+id;
        }

        public static FollowerDTO toDto(Follow entity){
            if(entity == null){
                return null;
            }

            Long id = entity.getFollowMember().getId();
            String nickname = entity.getFollowMember().getNickname();

            return FollowerDTO.builder()
                    .id(id)
                    .nickname(nickname)
                    .build();
        }
    }

    @Getter
    private static class FollowingDTO {
        Long id;
        String nickname;
        String image;

        @Builder
        private FollowingDTO(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
            this.image = "/api/member/image/"+id;
        }

        public static FollowingDTO toDto(Follow entity){
            if(entity == null){
                return null;
            }

            Long id = entity.getMember().getId();
            String nickname = entity.getMember().getNickname();

            return FollowingDTO.builder()
                    .id(id)
                    .nickname(nickname)
                    .build();
        }
    }
}
