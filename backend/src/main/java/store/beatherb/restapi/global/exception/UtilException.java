package store.beatherb.restapi.global.exception;

public class UtilException extends BeatHerbException {
    public UtilException(ErrorCode errorCode){
        super(errorCode);
    }
}