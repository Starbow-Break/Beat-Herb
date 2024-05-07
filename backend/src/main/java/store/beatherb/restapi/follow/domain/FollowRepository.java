package store.beatherb.restapi.follow.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<List<Follow>> findByMemberId(Long id);

    Optional<Follow> findByMemberIdAndFollowMemberId(Long memberId, Long followId);
    Optional<List<Follow>> findByFollowMemberId(Long followMemberId);
}
