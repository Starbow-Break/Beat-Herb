package store.beatherb.restapi.live.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MasterMemberResponse {
    private Long id;
    private String name;
}
