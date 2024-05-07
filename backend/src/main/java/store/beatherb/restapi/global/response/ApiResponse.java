package store.beatherb.restapi.global.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private int code;
    private List<String> message;
    private T data;
    public static<T> ApiResponse<T> successWithData(T data){
        return new ApiResponse<>(200,null,data);
    }
    public static ApiResponse<?> successWithoutData(){
        return new ApiResponse<>(200,null,null);
    }
    public static<T> ApiResponse<T> of(int code,T data){
        return new ApiResponse<>(code,null,data);
    }
    public static<T> ApiResponse<T> of(HttpStatus code, T data){
        return new ApiResponse<>(code.value(),null,data);
    }

    public static<T> ApiResponse<T> of(HttpStatus code,List<String> message, T data){
        return new ApiResponse<>(code.value(),message,data);
    }

    public static<T> ApiResponse<T> fail(int code, List<String> message){
        return new ApiResponse<>(code,message,null);
    }
}
