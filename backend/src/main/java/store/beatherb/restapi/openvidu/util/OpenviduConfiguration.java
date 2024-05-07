package store.beatherb.restapi.openvidu.util;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import store.beatherb.restapi.openvidu.property.OpenviduProperties;

@Configuration
@RequiredArgsConstructor
public class OpenviduConfiguration {

    private final OpenviduProperties openviduProperties;

    @Bean
    public WebClient openviduWebClient() {
        return WebClient.builder()
                .baseUrl(openviduProperties.getBaseUrl())
                .defaultHeader("Authorization", openviduProperties.getDefaultHeader())
                .build();
    }
}
