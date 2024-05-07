package store.beatherb.restapi.global.jwt.dto.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class JWTException extends BeatHerbException {
    public JWTException(ErrorCode errorCode){
        super(errorCode);
    }
}
