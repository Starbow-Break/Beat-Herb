package store.beatherb.restapi.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyRepository extends JpaRepository<Verify, String> {
    Optional<Verify> findByUuid(String uuid);

    Optional<Verify> findById(Long id);


}
