package com.kalsym.analytic.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Sarosh
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class HttpResponse {

    public HttpResponse(String requestUri) {
        this.timestamp = DateTimeUtil.currentTimestamp();
        this.path = requestUri;
    }

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private Object data;
    private String path;

    /**
     * *
     * Sets success and message as reason phrase of provided status.
     *
     * @param status
     */
    public void setSuccessStatus(HttpStatus status) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
    }

    /**
     * *
     * Sets success and message as reason phrase of provided status.
     *
     * @param status
     */
    public void setSuccessStatus(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    /**
     * *
     * Sets status and custom message.
     *
     * @param status
     */
    public void setErrorStatus(HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    /**
     * *
     * Sets status and custom message.
     *
     * @param status
     * @param message
     */
    public void setErrorStatus(HttpStatus status, String message) {
        this.status = status.value();
        this.error = message;
    }
}
