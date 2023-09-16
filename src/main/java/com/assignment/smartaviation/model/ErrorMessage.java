package com.assignment.smartaviation.model;

import lombok.Data;

@Data
public class ErrorMessage {
    private int statusCode;
    private String errorCode;
    private String errorMessage;
}
