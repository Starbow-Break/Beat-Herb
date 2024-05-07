package store.beatherb.restapi.global.auth.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class AuthException extends BeatHerbException {
    public AuthException(ErrorCode errorCode){
        super(errorCode);
    }
}
