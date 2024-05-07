package store.beatherb.restapi.content.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode {

    FAVORITE_FIND_ERROR(404, "FAV_01", "좋아요가 존재하지 않습니다."),
    ALREADY_EXISTS(400, "FAV_02" , "이미 좋아요 하셨습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
