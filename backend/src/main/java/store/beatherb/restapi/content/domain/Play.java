package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="play")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(name="hit")
    private Integer hit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Play(Content content, int hit, LocalDateTime createdAt) {
        this.content = content;
        this.hit = hit;
        this.createdAt = createdAt;
    }
}
