package br.com.juno.integration.api.base.exception;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetail implements Serializable {

    private static final long serialVersionUID = 6629135241307840566L;

    private String timestamp;
    private String status;
    private String error;
    private List<Details> details;
    private String message;
    private String path;

    protected ErrorDetail() {
        // NTD
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<Details> getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append(timestamp);
        builder.append(status);
        builder.append(error);
        builder.append(details);
        builder.append(path);
        return builder.toString();
    }

    public static class Details {

        private String field;
        private String message;
        private String errorCode;
        private HttpStatus httpStatus;

        protected Details() {
            // NTD
        }

        public boolean isValidationFieldError() {
            return StringUtils.isNotBlank(field);
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

    }

}
