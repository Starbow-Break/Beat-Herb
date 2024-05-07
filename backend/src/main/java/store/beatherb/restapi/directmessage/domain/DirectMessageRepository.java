package store.beatherb.restapi.directmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.beatherb.restapi.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage,Long> {

    List<DirectMessage> findByCreatedAtAfterAndSenderOrReceiver(LocalDateTime localDateTime,Member sender, Member receiver);
}
