package store.beatherb.restapi.content.dto.response;

import lombok.*;
import store.beatherb.restapi.content.domain.Content;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@AllArgsConstructor
public class SearchResultResponse {
    List<Content> contents;
}
