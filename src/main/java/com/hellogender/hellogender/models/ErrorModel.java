package com.hellogender.hellogender.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorModel {
    String message;
    HttpStatus httpStatus;
}
