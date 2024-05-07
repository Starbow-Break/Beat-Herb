package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.member.domain.Member;

@Entity
@Table(name="star")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    public Star(Content content, Member member) {
        this.content = content;
        this.member = member;
    }
}
