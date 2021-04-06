package com.hellogender.hellogender.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorModel {
    String message;
    HttpStatus httpStatus;
}
