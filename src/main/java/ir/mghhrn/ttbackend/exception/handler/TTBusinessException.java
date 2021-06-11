package ir.mghhrn.ttbackend.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@Getter
public class TTBusinessException extends RuntimeException {
    private static final long serialVersionUID = 231828244649427432L;
    private static final Locale LOCALE_FARSI = Locale.forLanguageTag("fa");
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;

    private final Exception cause;
    private final String messageKey;
    private final Object[] messageArgs;
    private final HttpStatus httpStatus;
    private final Locale locale;


    public TTBusinessException(String messageKey) {
        this.cause = null;
        this.messageKey = messageKey;
        this.messageArgs = null;
        this.httpStatus = DEFAULT_STATUS;
        locale = LOCALE_FARSI;
    }

    public TTBusinessException(String messageKey, HttpStatus httpStatus) {
        this.cause = null;
        this.messageKey = messageKey;
        this.messageArgs = null;
        this.httpStatus = httpStatus;
        locale = LOCALE_FARSI;
    }

    public TTBusinessException(String messageKey, Object[] messageArgs) {
        this.cause = null;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.httpStatus = DEFAULT_STATUS;
        locale = LOCALE_FARSI;
    }

    public TTBusinessException(Exception exception, String messageKey, Object[] messageArgs) {
        this.cause = exception;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.httpStatus = DEFAULT_STATUS;
        locale = LOCALE_FARSI;
    }

    public TTBusinessException(Exception exception, String messageKey, Object[] messageArgs, HttpStatus httpStatus) {
        this.cause = exception;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.httpStatus = httpStatus;
        this.locale = LOCALE_FARSI;
    }

    public TTBusinessException(Exception exception, String messageKey, Object[] messageArgs, HttpStatus httpStatus, Locale locale) {
        this.cause = exception;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.httpStatus = httpStatus;
        this.locale = locale;
    }
}
