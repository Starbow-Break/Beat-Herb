package store.beatherb.restapi.interest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.content.domain.HashTag;
import store.beatherb.restapi.member.domain.Member;

@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    @Builder
    public Interest(Member member, HashTag hashTag){
        this.member = member;
        this.hashTag = hashTag;
    }
}
