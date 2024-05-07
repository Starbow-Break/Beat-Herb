package store.beatherb.restapi.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakao(String sub);
    Optional<Member> findByNaver(String sub);
    Optional<Member> findByGoogle(String sub);

    List<Member> findByNicknameContains(String nickname);


}
