package store.beatherb.socket.controller.valid.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "socketToken", timeToLive = 30)
public class SocketToken {


    @Id
    private String id;


    @Indexed
    private String uuid;
    @Getter
    private Long memberId;

    @Builder
    public SocketToken(String uuid, Long memberId) {
        this.uuid = uuid;
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "SocketToken{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", memberId=" + memberId +
                '}';
    }
}
