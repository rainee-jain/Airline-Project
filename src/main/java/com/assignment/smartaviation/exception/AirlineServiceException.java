package com.assignment.smartaviation.exception;

import lombok.Data;

@Data
public class AirlineServiceException extends RuntimeException{

    public AirlineServiceException() { super();}

    private String errorCode;
    private String errorMessage;
    private int status;

    public static Builder getBuilder() {return new Builder();}

    public static class Builder {
        private String errorCode;
        private String errorMessage;
        private int status;


        public Builder errorCode(String code) {
            this.errorCode = code;
            return this;
        }

        public Builder errorMessage(String message) {
            this.errorMessage = message;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public AirlineServiceException build() {
            AirlineServiceException airlineServiceException = new AirlineServiceException();
            airlineServiceException.errorCode = this.errorCode;
            airlineServiceException.errorMessage = this.errorMessage;
            airlineServiceException.status = this.status;
            return airlineServiceException;
        }
    }
}
