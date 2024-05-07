package store.beatherb.restapi.content.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum CreatorErrorCode implements ErrorCode {

    CREATOR_NOT_FOUND(404, "CREATOR_01", "해당 정보를 찾을 수 없습니다."),
    ALREADY_AGREE(400, "CREATOR_02", "이미 동의/거부 하셨습니다.");



    private final int statusCode;
    private final String errorCode;
    private final String message;
}
