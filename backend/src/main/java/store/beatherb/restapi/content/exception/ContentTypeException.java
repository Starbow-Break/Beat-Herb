package store.beatherb.restapi.content.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class ContentTypeException extends BeatHerbException {
    public ContentTypeException(ErrorCode errorCode){
        super(errorCode);
    }
}
