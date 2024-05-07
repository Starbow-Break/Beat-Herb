package store.beatherb.socket.websocket.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import store.beatherb.socket.controller.valid.service.SocketTokenValidService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class WebSocketInterceptor implements HandshakeInterceptor {
    private final SocketTokenValidService socketTokenValidService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("beforeHandShake Call");

        String query = request.getURI().getQuery();
        if (query == null) {
            return false;
        }
        String[] queryParams = query.split("&"); // &를 기준으로 쿼리 파라미터를 분리
        String token = null;
        for (String param : queryParams) {
            String[] keyValue = param.split("="); // =를 기준으로 파라미터 이름과 값 분리
            if (keyValue.length == 2 && keyValue[0].equals("token")) {
                token = keyValue[1]; // 토큰 값 추출
                break;
            }
        }
        log.info("Token = [{}]" , token);
        if (token != null) {
            Long id = socketTokenValidService.getMemberId(token);

            log.info("Long id = [{}]" , id);
            if (id != null) {
                attributes.put("id", id);
                return true;
            }
        }
        log.info("beforeHandShake done!");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
