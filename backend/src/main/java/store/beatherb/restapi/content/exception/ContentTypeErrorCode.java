package store.beatherb.restapi.content.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum ContentTypeErrorCode implements ErrorCode {
    CONTENT_TYPE_NOT_FOUND(404, "CONT_TYPE_01", "컨텐츠 타입이 존재하지 않습니다.");

    private int statusCode;
    private String errorCode;
    private String message;
}
