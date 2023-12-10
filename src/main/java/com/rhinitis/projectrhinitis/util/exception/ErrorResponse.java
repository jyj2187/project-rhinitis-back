package com.rhinitis.projectrhinitis.util.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String divisionCode;
    private String message;

    public ErrorResponse(BusinessLogicException ex){
        this.status = ex.getExceptionCode().getStatus();
        this.divisionCode = ex.getExceptionCode().getDivisionCode();
        this.message = ex.getExceptionCode().getMessage();
    }
}
