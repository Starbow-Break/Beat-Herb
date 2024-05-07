package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.content.domain.embed.ContentTypeEnum;

@Entity
@Table(name="content_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name="type")
    private ContentTypeEnum type;

    @Builder
    public ContentType(ContentTypeEnum type) {
        this.type = type;
    }
}
