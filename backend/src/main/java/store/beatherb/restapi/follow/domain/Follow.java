package store.beatherb.restapi.follow.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.member.domain.Member;

@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "follow_member_id")
    private Member followMember;

    @Builder
    public Follow(Member member, Member followMember){
        this.member = member;
        this.followMember = followMember;
    }
}
