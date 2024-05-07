package store.beatherb.restapi.content.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.beatherb.restapi.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum ContentErrorCode implements ErrorCode {

    CONTENT_NOT_FOUND(404, "CONT_01", "컨텐츠가 존재하지 않습니다."),
    LIST_HAS_NOT_CONTENT(404, "CONT_02", "리스트에 컨텐트가 담겨 있지 않습니다."),
    LIST_HAS_NOT_COMMENT(404, "CONT_03", "리스트에 코멘트가 담겨 있지 않습니다."),
    HASHTAG_NOT_FOUND(404, "CONT_04", "해당 해시태그가 존재하지 않습니다."),
    MUSIC_NOT_VALID(404, "CONT_05", "올바르지 않은 음악 파일 입나다."),
    CONTENT_IMAGE_NOT_VALID(404, "CONT_06", "올바르지 않은 이미지 파일 입나다."),
    CONTENT_TYPE_NOT_VALID(404,"CONT_07","올바르지 않은 콘텐츠 타입입니다.");


    private final int statusCode;
    private final String errorCode;
    private final String message;
}
