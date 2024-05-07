package store.beatherb.restapi.content.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class ContentException extends BeatHerbException {
    public ContentException(ErrorCode errorCode){
        super(errorCode);
    }
}
