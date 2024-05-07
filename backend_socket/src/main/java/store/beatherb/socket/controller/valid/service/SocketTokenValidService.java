package store.beatherb.socket.controller.valid.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import store.beatherb.socket.controller.valid.domain.SocketToken;
import store.beatherb.socket.controller.valid.domain.SocketTokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocketTokenValidService {
//    private final SocketTokenRepository socketTokenRepository;

    private final RedisTemplate<String,Integer> redisTemplate;

    public Long getMemberId(String uuid){
        log.info("uuid = [{}]",uuid);
//        SocketToken socketToken = socketTokenRepository.findByUuid(uuid).orElse(null);
        Integer id = redisTemplate.opsForValue().get(uuid);
        if(id == null){
            log.info("call socketToken null");
//            SocketToken socketToken1 = SocketToken.builder()
//                    .memberId(1L)
//                    .uuid(uuid)
//                    .build();
//            socketTokenRepository.save(socketToken1);
            return null;
        }
//        Long id = socketToken.getMemberId();
//        socketTokenRepository.delete(socketToken);
        log.info("call delete");
        redisTemplate.delete(uuid);
        return  id.longValue();
    }
}
