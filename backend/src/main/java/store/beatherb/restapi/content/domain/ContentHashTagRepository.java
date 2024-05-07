package store.beatherb.restapi.content.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentHashTagRepository extends JpaRepository<ContentHashTag, Long> {
    List<ContentHashTag> findByHashTag(HashTag hashTag);
}
