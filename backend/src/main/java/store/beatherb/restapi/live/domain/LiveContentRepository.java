package store.beatherb.restapi.live.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import store.beatherb.restapi.member.domain.Member;

import java.util.Optional;

public interface LiveContentRepository extends JpaRepository<LiveContent, Long> {
    Optional<LiveContent> findById(Long id);
}
