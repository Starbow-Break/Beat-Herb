package store.beatherb.restapi.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UtilErrorCode implements ErrorCode{
    DECODE_ERROR(500, "UTIL_01", "디코딩 과정에서 에러가 발생했습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
