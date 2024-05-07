package store.beatherb.restapi.content.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum HashTagErrorCode implements ErrorCode {
    HASHTAG_IS_NOT_EXIST(400, "HASHTAG_01", "해시태그 정보가 존재하지 않습니다.");

    private int statusCode;
    private String errorCode;
    private String message;
}
