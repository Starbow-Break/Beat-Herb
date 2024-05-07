package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="content_hashtag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    /*
    TODO: 해시태그 테이블이랑 연관관계 설정
     */
    @ManyToOne
    @JoinColumn(name="hashtag_id")
    private HashTag hashTag;

    @ManyToOne
    @JoinColumn(name="content_id")
    @Setter
    private Content content;


    @Builder

    public ContentHashTag(HashTag hashTag, Content content) {
        this.hashTag = hashTag;
        this.content = content;
    }
}
