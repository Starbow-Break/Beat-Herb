package store.beatherb.restapi.content.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findById(Long id);
    List<HashTag> findAll();
    List<HashTag> findByNameStartsWith(String name);
}
