package store.beatherb.restapi.live.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class LiveCreateRequest {
    @NotNull(message = "title 이 필요합니다")
    String title;
    @NotNull(message = "describe 가 필요합니다.")
    String describe;
    Set<Long> guestIdList;
    Set<Long> contentIdList;

    public LiveCreateRequest(String title, String describe, Set<Long> guestIdList,Set<Long> contentIdList) {
        this.title = title;
        this.describe = describe;
        this.guestIdList = guestIdList;
        if(guestIdList ==null){
            this.guestIdList = new HashSet<>();
        }
        if(contentIdList == null){
            contentIdList = new HashSet<>();
        }
        this.contentIdList = contentIdList;
    }
}
