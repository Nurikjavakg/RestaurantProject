package peaksoft.exceptions;

import lombok.Builder;
import org.springframework.http.HttpStatus;
@Builder
public record ExceptionResponse(HttpStatus httpStatus,String exceptionClassName,String massage) {
}
