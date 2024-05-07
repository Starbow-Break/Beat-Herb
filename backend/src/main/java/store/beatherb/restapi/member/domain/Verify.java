package store.beatherb.restapi.member.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import store.beatherb.restapi.member.domain.Member;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "verify")
@Getter
@Setter
public class Verify {
    @Builder
    public Verify(Member member){
        this.member = member;
        this.uuid = UUID.randomUUID().toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Optional<Member> getMember() {
        return Optional.ofNullable(member);
    }

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
}
