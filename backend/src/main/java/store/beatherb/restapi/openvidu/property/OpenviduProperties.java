package store.beatherb.restapi.openvidu.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@Getter
public class OpenviduProperties {

    @Value("${openvidu.baseURL}")
    private String baseUrl;

    @Value("${openvidu.secret}")
    private String secretKey;

    public String getDefaultHeader() {
        String originalInput = "OPENVIDUAPP:" + secretKey;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        return "Basic " + encodedString;
    }
}
