package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.member.domain.Member;
import java.time.LocalDateTime;

@Entity
@Table(name="comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name="writer_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="content_id")
    private Content content;

    @Column(name="body")
    private String body;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Builder
    public Comment(Member member, Content content, String body, LocalDateTime createdAt) {
        this.member = member;
        this.content = content;
        this.body = body;
        this.createdAt = createdAt;
    }
}
