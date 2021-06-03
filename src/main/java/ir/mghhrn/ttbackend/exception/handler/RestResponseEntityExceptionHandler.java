package ir.mghhrn.ttbackend.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> methd(Exception exception, WebRequest request) {
        ThrowableProblem problem = Problem.builder()
                .withType(URI.create(request.getContextPath()))
                .withTitle("Unbelievable!")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
