package store.beatherb.restapi.live.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.beatherb.restapi.member.domain.Member;

import java.util.Optional;

public interface LiveRepository extends JpaRepository<Live, Long> {
    Optional<Live> findByMember(Member member);
}
