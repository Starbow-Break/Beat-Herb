package store.beatherb.restapi.openvidu.service;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriBuilder;
import store.beatherb.restapi.live.domain.dto.response.LiveJoinResponse;
import store.beatherb.restapi.live.exception.LiveErrorCode;
import store.beatherb.restapi.live.exception.LiveException;
import store.beatherb.restapi.openvidu.dto.response.OpenViduJoinSessionResponse;
import store.beatherb.restapi.openvidu.property.OpenviduSessionProperties;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenviduService {

    private final WebClient openviduWebClient;
    private final OpenviduSessionProperties openviduSessionProperties;

    public Boolean createSessionById(Long id) {
        try {
            openviduWebClient
                    .method(HttpMethod.POST)
                    .uri(builder -> builder
                            .path(openviduSessionProperties.getOpenviduSessionPath())
                            .build())
                    .bodyValue(CreateSessionRequest.builder().customSessionId(id).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return true;
        }catch (WebClientException e){

            throw new LiveException(LiveErrorCode.LIVE_ALREADY_EXIST);
        }

    }
    public OpenViduJoinSessionResponse joinSessionByIdAndRole(Long id, String role){
        try {

            return openviduWebClient
                .method(HttpMethod.POST)
                .uri(builder -> builder
                        .path(openviduSessionProperties.getOpenviduSessionPath() + "/" + id + "/connection")
                        .build())
                .bodyValue(JoinSessionRequest.builder().role(role).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OpenViduJoinSessionResponse.class)
                .block();

        }catch (WebClientException e){
            throw new LiveException(LiveErrorCode.LIVE_IS_NOT_EXIST);
        }
    }

    public void deleteSessionById(Long id) {


        try {

            log.info("deleteSessionById = [{}]",openviduSessionProperties.getOpenviduSessionPath() + "/" + id  );
            openviduWebClient
                    .method(HttpMethod.DELETE)
                    .uri(builder -> builder
                            .path(openviduSessionProperties.getOpenviduSessionPath() + "/" + id )
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        }
        catch (WebClientException e){
            e.printStackTrace();
        }
    }


    @Getter
    private static class CreateSessionRequest{
        String customSessionId;

        @Builder
        public CreateSessionRequest(Long customSessionId) {
            this.customSessionId = String.valueOf(customSessionId);
        }
    }

    @Getter
    @Builder
    private static class JoinSessionRequest{
        String role;

    }


}
