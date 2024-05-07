package store.beatherb.restapi.live.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class LiveException extends BeatHerbException {
    public LiveException(ErrorCode errorCode){
        super(errorCode);
    }
}
