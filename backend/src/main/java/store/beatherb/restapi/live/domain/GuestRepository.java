package store.beatherb.restapi.live.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import store.beatherb.restapi.member.domain.Member;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByMemberAndLive(Member member,Live live);
}
