package store.beatherb.socket.controller.valid.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocketTokenRepository extends CrudRepository<SocketToken, String> {
    Optional<SocketToken> findByUuid(String uuid);
}
