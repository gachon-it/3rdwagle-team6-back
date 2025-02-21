package wagle.team6.clothes.clothes.apiPayload;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {

    private HttpStatus httpStatus;

    private boolean isSuccess;
    private String code;
    private String message;

    public boolean getIsSuccess(){return isSuccess;}
}