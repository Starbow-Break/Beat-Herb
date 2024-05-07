package store.beatherb.restapi.content.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.ContentHashTag;
import store.beatherb.restapi.content.domain.ContentType;
import store.beatherb.restapi.content.domain.Creator;
import store.beatherb.restapi.content.domain.embed.ContentTypeEnum;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HashTagUseResponse {
    List<HashTagUseResponseMember> memberList;
    List<HashTagUseResponseContent> vocalList;
    List<HashTagUseResponseContent> melodyList;
    List<HashTagUseResponseContent> soundTrackList;

    @Builder
    public HashTagUseResponse(List<HashTagUseResponseMember> memberList, List<HashTagUseResponseContent> vocalList, List<HashTagUseResponseContent> melodyList, List<HashTagUseResponseContent> soundTrackList) {
        this.memberList = memberList;
        this.vocalList = vocalList;
        this.melodyList = melodyList;
        this.soundTrackList = soundTrackList;
    }

    public static HashTagUseResponse toDto(List<Interest> interestList, List<ContentHashTag> contentHashTagList) {

        List<HashTagUseResponseMember> memberList = new ArrayList<>();
        List<HashTagUseResponseContent> vocalList = new ArrayList<>();
        List<HashTagUseResponseContent> melodyList = new ArrayList<>();
        List<HashTagUseResponseContent> soundTrackList = new ArrayList<>();
        for (Interest interest : interestList) {
            Member member = interest.getMember();
            HashTagUseResponseMember hashTagUseResponseMember = HashTagUseResponseMember.toDto(member);
            memberList.add(hashTagUseResponseMember);
        }
        for (ContentHashTag contentHashTag : contentHashTagList) {
            Content c = contentHashTag.getContent();
            HashTagUseResponseContent hashTagUseResponseContent = HashTagUseResponseContent.toDto(c);

            if (c.getContentType().getType() == ContentTypeEnum.MELODY) {
                melodyList.add(hashTagUseResponseContent);
            } else if (c.getContentType().getType() == ContentTypeEnum.VOCAL) {
                vocalList.add(hashTagUseResponseContent);
            } else {
                soundTrackList.add(hashTagUseResponseContent);
            }
        }
        return HashTagUseResponse.builder()
                .memberList(memberList)
                .melodyList(melodyList)
                .vocalList(vocalList)
                .soundTrackList(soundTrackList)
                .build();
    }


    @Getter
    public static class HashTagUseResponseMember {
        Long id;
        String nickname;
        String image;

        @Builder
        public HashTagUseResponseMember(Long id, String nickname, String image) {
            this.id = id;
            this.nickname = nickname;
            this.image = image;
        }

        public static HashTagUseResponseMember toDto(Member member) {
            return HashTagUseResponseMember.builder()
                    .id(member.getId())
                    .image("/api/member/image/" + member.getId())
                    .nickname(member.getNickname())
                    .build();
        }
    }

    @Getter
    public static class HashTagUseResponseContent {
        Long id;
        //id 제목 creatorList
        List<HashTagUseResponseContentMember> creatorList;
        String title;
        String contentType;
        String image;

        @Builder
        public HashTagUseResponseContent(Long id, List<HashTagUseResponseContentMember> creatorList, String title, String contentType, String image) {
            this.id = id;
            this.creatorList = creatorList;
            this.title = title;
            this.contentType = contentType;
            this.image = image;
        }

        public static HashTagUseResponseContent toDto(Content c) {
            Long id = c.getId();
            List<HashTagUseResponseContentMember> creatorList = new ArrayList<>();
            String title = c.getTitle();
            String contentType = c.getContentType().getType().name();
            for (Creator creator : c.getCreatorList()) {
                HashTagUseResponseContentMember hashTagUseResponseContentMember = HashTagUseResponseContentMember.toDto(creator.getCreator());
                creatorList.add(hashTagUseResponseContentMember);
            }

            return HashTagUseResponseContent.builder()
                    .id(id)
                    .creatorList(creatorList)
                    .title(title)
                    .contentType(contentType)
                    .build();
        }
    }

    @Getter

    public static class HashTagUseResponseContentMember {
        Long id;
        String nickname;

        @Builder
        public HashTagUseResponseContentMember(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        public static HashTagUseResponseContentMember toDto(Member member) {
            return HashTagUseResponseContentMember.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build();
        }
    }
}
