package store.beatherb.restapi.global.exception;

public interface ErrorCode {
    int getStatusCode();
    String getErrorCode();
    String getMessage();
}
