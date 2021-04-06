package com.hellogender.hellogender.controllers;

import com.hellogender.hellogender.Configuration;
import com.hellogender.hellogender.models.ErrorModel;
import com.hellogender.hellogender.models.Gender;
import com.hellogender.hellogender.services.GenderRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class GenderRecognitionController {

    private final GenderRecognitionService genderRecognitionService;

    @GetMapping("/recognizeGender")
    public String recognizeGender(@RequestParam @NotBlank(message = "Name must not be blank") String name,
                                  @RequestParam String algorithmVariant,
                                  @RequestHeader(value = Configuration.versionHeader, required = false) String version) {
        return genderRecognitionService.recognizeGender(name, algorithmVariant, version).name();
    }

    @GetMapping("/tokenList")
    public List<String> getTokenList(@RequestParam Gender gender) {
        return genderRecognitionService.getTokenList(gender);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorModel> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorModel(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorModel> handleConstrainViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorModel(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
