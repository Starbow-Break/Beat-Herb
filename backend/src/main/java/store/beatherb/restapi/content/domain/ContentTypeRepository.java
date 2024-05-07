package store.beatherb.restapi.content.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import store.beatherb.restapi.content.domain.embed.ContentTypeEnum;

import java.util.Optional;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    Optional<ContentType> findByType(ContentTypeEnum contentTypeEnum);
}
