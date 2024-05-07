package store.beatherb.restapi.content.exception;

import store.beatherb.restapi.global.exception.BeatHerbException;
import store.beatherb.restapi.global.exception.ErrorCode;

public class FavoriteException extends BeatHerbException {
    public FavoriteException(ErrorCode errorCode){
        super(errorCode);
    }
}
