package br.com.juno.integration.api.base.exception;

import br.com.juno.integration.api.model.ErrorDetail;

public class JunoApiException extends RuntimeException {

    private static final long serialVersionUID = -3935323981105727201L;

    public JunoApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JunoApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public JunoApiException(ErrorDetail errorDetail) {
        super(errorDetail != null ? errorDetail.toString() : null);
    }

    public JunoApiException(String message) {
        super(message);
    }

    public JunoApiException(Throwable cause) {
        super(cause);
    }

}
