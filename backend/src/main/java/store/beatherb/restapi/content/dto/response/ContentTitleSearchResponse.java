package store.beatherb.restapi.content.dto.response;

import lombok.Builder;
import lombok.Getter;
import store.beatherb.restapi.content.domain.Content;
import store.beatherb.restapi.content.domain.ContentHashTag;
import store.beatherb.restapi.content.domain.Creator;
import store.beatherb.restapi.content.domain.embed.ContentTypeEnum;
import store.beatherb.restapi.interest.domain.Interest;
import store.beatherb.restapi.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ContentTitleSearchResponse {
    List<ContentTitleSearchResponseContent> vocalList;
    List<ContentTitleSearchResponseContent> melodyList;
    List<ContentTitleSearchResponseContent> soundTrackList;

    @Builder
    public ContentTitleSearchResponse( List<ContentTitleSearchResponseContent> vocalList, List<ContentTitleSearchResponseContent> melodyList, List<ContentTitleSearchResponseContent> soundTrackList) {

        this.vocalList = vocalList;
        this.melodyList = melodyList;
        this.soundTrackList = soundTrackList;
    }

    public static ContentTitleSearchResponse toDto(List<Content> contentList) {

        List<ContentTitleSearchResponseContent> vocalList = new ArrayList<>();
        List<ContentTitleSearchResponseContent> melodyList = new ArrayList<>();
        List<ContentTitleSearchResponseContent> soundTrackList = new ArrayList<>();

        for (Content c: contentList) {
            ContentTitleSearchResponseContent contentTitleSearchResponseContent = ContentTitleSearchResponseContent.toDto(c);

            if (c.getContentType().getType() == ContentTypeEnum.MELODY) {
                melodyList.add(contentTitleSearchResponseContent);
            } else if (c.getContentType().getType() == ContentTypeEnum.VOCAL) {
                vocalList.add(contentTitleSearchResponseContent);
            } else {
                soundTrackList.add(contentTitleSearchResponseContent);
            }
        }
        return ContentTitleSearchResponse.builder()
                .melodyList(melodyList)
                .vocalList(vocalList)
                .soundTrackList(soundTrackList)
                .build();
    }


    @Getter
    public static class ContentTitleSearchResponseContent{
        Long id;
        String type;
        String image;
        String title;
        List<ContentTitleSearchResponseContentCreator> creatorList;

        @Builder
        public ContentTitleSearchResponseContent(Long id, String type, String title, List<ContentTitleSearchResponseContentCreator> creatorList) {
            this.id = id;
            this.type = type;
            this.image = "/api/content/image/"+id;
            this.title = title;
            this.creatorList = creatorList;
        }

        public static ContentTitleSearchResponseContent toDto(Content entity){
            Long id = entity.getId();
            String title = entity.getTitle();
            String type = entity.getContentType().getType().name();
            List<ContentTitleSearchResponseContentCreator> creatorList = new ArrayList<>();
            for(Creator creator: entity.getCreatorList()){
                creatorList.add(
                        ContentTitleSearchResponseContentCreator.toDto(creator.getCreator())
                );
            }
            return ContentTitleSearchResponseContent.builder()
                    .id(id)
                    .title(title)
                    .type(type)
                    .creatorList(creatorList)
                    .build();
        }

        @Getter
        public static class ContentTitleSearchResponseContentCreator{
            Long id;
            String nickname;

            @Builder
            public ContentTitleSearchResponseContentCreator(Long id, String nickname) {
                this.id = id;
                this.nickname = nickname;
            }
            public static ContentTitleSearchResponseContentCreator toDto(Member member){
                Long id = member.getId();
                String nickname = member.getNickname();
                return ContentTitleSearchResponseContentCreator.builder()
                        .id(id)
                        .nickname(nickname)
                        .build();
            }
        }

    }


}
