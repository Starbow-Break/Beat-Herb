package store.beatherb.restapi.live.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "live_id")
    @Setter
    private Live live;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Member member;

    @Column(name = "agree", nullable = false)
    private Boolean agree;

    @Builder
    public Guest(Live live, Member member, Boolean agree){
        this.live = live;
        this.member = member;
        this.agree = agree;
    }
}
