package store.beatherb.restapi.openvidu.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@Getter
public class OpenviduSessionProperties {


    private final String openviduSessionPath = "/openvidu/api/sessions";


}
