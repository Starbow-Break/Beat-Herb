package store.beatherb.restapi.content.dto.response;

import lombok.*;
import store.beatherb.restapi.content.domain.*;
import store.beatherb.restapi.content.dto.*;
import store.beatherb.restapi.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ContentDetailResponse {

    List<ContentDetailResponseMember> creatorList;
    String title;
    List<ContentDetailResponseHashTag> hashTagList;
    String lyrics;
    String describe;
    int hit;
    boolean processed;
    String contentType;
    List<ContentDetailResponseOrder> inOrderList;
    OutOrder outOrder;
    String image;
    List<ContentDetailResponseComment> commentList;
    boolean star;




    public static ContentDetailResponse toDto(Content entity,boolean star,String lyrics, String describe) {
        String title = entity.getTitle();
        List<Creator> creatorList = entity.getCreatorList();
        List<ContentHashTag> contentHashTagList = entity.getContentHashTagList();



        int hit = entity.getHit();
        ContentType contentType = entity.getContentType();
        boolean processed = entity.isProcessed();


        LocalDateTime createdAt = entity.getCreatedAt();
        List<InOrder> inOrderList = entity.getInOrderList();
        List<InOrder> outOrderList = entity.getOutOrderList();

        List<ContentDetailResponseMember> creatorDtoList = new ArrayList<>();
        for (Creator c : creatorList) {
            if (c.isAgree()) {

                creatorDtoList.add(ContentDetailResponseMember.toDto(c));
            }
        }

        List<ContentDetailResponseHashTag> hashTagDtoList = new ArrayList<>();
        for (ContentHashTag contentHashTag : contentHashTagList) {
            HashTag hashTag = contentHashTag.getHashTag();
            hashTagDtoList.add(
                    ContentDetailResponseHashTag.toDto(hashTag)
            );
        }
        List<ContentDetailResponseOrder> contentDetailResponseInorderList = new ArrayList<>();
        for (InOrder inOrder : inOrderList) {
            contentDetailResponseInorderList.add(

                    ContentDetailResponseOrder.toInOrderDto(inOrder)
            );
        }
        OutOrder outOrder = OutOrder.toDto(outOrderList);

        List<Comment> commentEntityList = entity.getCommentList();
        List<ContentDetailResponseComment> commentList = new ArrayList<>();
        for(Comment c: commentEntityList){
            commentList.add(ContentDetailResponseComment.toDto(c));
        }




        return ContentDetailResponse.builder()
                .title(title)
                .creatorList(creatorDtoList)
                .hashTagList(hashTagDtoList)
                .lyrics(lyrics)
                .hit(hit)
                .describe(describe)
                .processed(processed)
                .contentType(contentType.getType().name())
                .image("/api/content/image/" + entity.getId())
                .inOrderList(contentDetailResponseInorderList)
                .outOrder(outOrder)
                .commentList(commentList)
                .star(star)
                .build();

    }

    @Getter
    private static class ContentDetailResponseMember {
        Long id;
        String nickname;

        @Builder
        public ContentDetailResponseMember(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
        }

        public static ContentDetailResponseMember toDto(Creator creator) {
            Member member = creator.getCreator();
            return ContentDetailResponseMember.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build();
        }

        public static ContentDetailResponseMember toDto(Member member) {
            return ContentDetailResponseMember.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build();
        }
    }


    @Getter
    private static class ContentDetailResponseHashTag {
        Long id;
        String name;

        @Builder
        public ContentDetailResponseHashTag(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public static ContentDetailResponseHashTag toDto(HashTag entity) {
            return ContentDetailResponseHashTag.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        }
    }

    @Getter
    private static class ContentDetailResponseOrder {

        Long id;
        String title;
        String image;
        String type;
        List<ContentDetailResponseOrderCreator> creatorList;

        @Builder
        public ContentDetailResponseOrder(Long id, String title, String type,List<ContentDetailResponseOrderCreator> creatorList) {
            this.id = id;
            this.title = title;
            this.image = "/api/content/image/" + this.id;
            this.type = type;
            this.creatorList = creatorList;
        }

        public static ContentDetailResponseOrder toInOrderDto(InOrder inOrder) {
            Content content = inOrder.getRootContent();
            List<ContentDetailResponseOrderCreator> creatorList = new ArrayList<>();
            for(Creator creator : content.getCreatorList()){
                creatorList.add(
                        ContentDetailResponseOrderCreator.toDto(creator.getCreator())
                );
            }
            return ContentDetailResponseOrder.builder()
                    .id(content.getId())
                    .title(content.getTitle())
                    .type(content.getContentType().getType().name())
                    .creatorList(creatorList)
                    .build();
        }

        public static ContentDetailResponseOrder toOutOrderDto(InOrder inOrder) {
            Content content = inOrder.getChildContent();
            List<ContentDetailResponseOrderCreator> creatorList = new ArrayList<>();
            for(Creator creator : content.getCreatorList()){
                creatorList.add(
                        ContentDetailResponseOrderCreator.toDto(creator.getCreator())
                );
            }
            return ContentDetailResponseOrder.builder()
                    .id(content.getId())
                    .title(content.getTitle())
                    .type(content.getContentType().getType().name())
                    .creatorList(creatorList)
                    .build();
        }

        @Getter
        public static class ContentDetailResponseOrderCreator{
            Long id;
            String nickname;

            @Builder
            public ContentDetailResponseOrderCreator(Long id, String nickname) {
                this.id = id;
                this.nickname = nickname;
            }
            public static ContentDetailResponseOrderCreator toDto(Member member){
                Long id = member.getId();
                String nickname = member.getNickname();
                return ContentDetailResponseOrderCreator.builder()
                        .id(id)
                        .nickname(nickname)
                        .build();
            }

        }
    }

    //
    @Getter
    private static class OutOrder {
        List<ContentDetailResponseOrder> vocalList;
        List<ContentDetailResponseOrder> melodyList;
        List<ContentDetailResponseOrder> soundTrackList;

        @Builder
        public OutOrder(List<ContentDetailResponseOrder> vocalList, List<ContentDetailResponseOrder> melodyList, List<ContentDetailResponseOrder> soundTrackList) {
            this.vocalList = vocalList;
            this.melodyList = melodyList;
            this.soundTrackList = soundTrackList;
        }

        public static OutOrder toDto(List<InOrder> outOrderList) {
            List<ContentDetailResponseOrder> vocalList = new ArrayList<>();
            List<ContentDetailResponseOrder> melodyList = new ArrayList<>();
            List<ContentDetailResponseOrder> soundTrackList = new ArrayList<>();

            for (InOrder outOrder : outOrderList) {

                ContentDetailResponseOrder order = ContentDetailResponseOrder.toOutOrderDto(outOrder);
                if (order.type.equals("VOCAL")) {
                    vocalList.add(order);
                } else if (order.type.equals("MELODY")) {
                    melodyList.add(order);
                } else {
                    soundTrackList.add(order);
                }

            }
            return OutOrder.builder()
                    .melodyList(melodyList)
                    .vocalList(vocalList)
                    .soundTrackList(soundTrackList)
                    .build();

        }
    }

    @Getter
    public static class ContentDetailResponseComment{
        Long id;
        String body;
        ContentDetailResponseCommentMember member;
        @Builder
        public ContentDetailResponseComment(Long id, String body, ContentDetailResponseCommentMember member) {
            this.id = id;
            this.body = body;
            this.member = member;
        }

        public static ContentDetailResponseComment toDto(Comment comment){
            Long id = comment.getId();
            String body = comment.getBody();
            ContentDetailResponseCommentMember contentDetailResponseCommentMember = ContentDetailResponseCommentMember.toDto(comment.getMember());
            return ContentDetailResponseComment.builder()
                    .id(id)
                    .body(body)
                    .member(contentDetailResponseCommentMember)
                    .build();

        }
    }


    @Getter
    public static class ContentDetailResponseCommentMember {
        Long id;
        String nickname;
        String image;

        @Builder
        public ContentDetailResponseCommentMember(Long id, String nickname) {
            this.id = id;
            this.nickname = nickname;
            this.image = "/api/member/image/" +id;
        }

        public static ContentDetailResponseCommentMember toDto(Member member) {
            return ContentDetailResponseCommentMember.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .build();
        }
    }
}
