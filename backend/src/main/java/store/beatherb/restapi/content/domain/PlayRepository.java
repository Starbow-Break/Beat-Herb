package store.beatherb.restapi.content.domain;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import store.beatherb.restapi.content.dto.response.ContentListInterface;

import java.util.List;

public interface PlayRepository extends JpaRepository<Play, Long> {
    @Query(value = "SELECT c.id as id, title, c.content_writer_id as contentWriterId, count(*) as count FROM play p INNER JOIN content c ON p.content_id = c.id WHERE content_type_id = :content_type_id AND CAST(p.created_at AS DATE) = :date group by content_id order by count desc limit 5", nativeQuery = true)
    List<ContentListInterface> findByCreatedAtDate(@Param("date") String date, @Param("content_type_id") Long content_type_id);

    @Query(value = "SELECT c.id as id, title, c.content_writer_id as contentWriterId, count(*) as count FROM play p INNER JOIN content c ON p.content_id = c.id WHERE content_type_id = :content_type_id AND CAST(p.created_at AS DATE) BETWEEN :start_date AND :end_date group by content_id order by count desc limit 5", nativeQuery = true)
    List<ContentListInterface> findByCreatedAtPeriod(@Param("content_type_id") Long content_type_id, @Param("start_date") String start_date, @Param("end_date") String end_date);

    @Query(value = "SELECT c.id as id, title, c.content_writer_id as contentWriterId, count(*) as count FROM play p INNER JOIN content c ON p.content_id = c.id WHERE content_type_id = :content_type_id AND CAST(p.created_at AS DATE) = :date group by content_id order by count desc", nativeQuery = true)
    List<ContentListInterface> findByTotalCreatedAtDate(@Param("date") String date, @Param("content_type_id") Long content_type_id);

    @Query(value = "SELECT c.id as id, title, c.content_writer_id as contentWriterId, count(*) as count FROM play p INNER JOIN content c ON p.content_id = c.id WHERE content_type_id = :content_type_id AND CAST(p.created_at AS DATE) BETWEEN :start_date AND :end_date group by content_id order by count desc", nativeQuery = true)
    List<ContentListInterface> findByTotalCreatedAtPeriod(@Param("content_type_id") Long content_type_id, @Param("start_date") String start_date, @Param("end_date") String end_date);
}
