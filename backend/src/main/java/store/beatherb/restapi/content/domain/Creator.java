package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.member.domain.Member;

@Entity
@Table(name = "creator")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    private Member creator;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Setter
    @Column
    private boolean agree;

    public void setContent(Content content) {
        this.content = content;
    }

    @Builder
    public Creator(Member creator, boolean agree) {
        this.creator = creator;
        this.agree = agree;
    }
}
