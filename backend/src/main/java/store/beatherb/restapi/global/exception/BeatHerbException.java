package store.beatherb.restapi.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BeatHerbException extends RuntimeException{
    private final int statusCode;
    private final String errorCode;
    private final String message;

    public BeatHerbException(ErrorCode code){
        this.statusCode=code.getStatusCode();
        this.errorCode=code.getErrorCode();
        this.message=code.getMessage();
    }
}
