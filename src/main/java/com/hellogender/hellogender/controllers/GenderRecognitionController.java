package com.hellogender.hellogender.controllers;

import com.hellogender.hellogender.models.ErrorModel;
import com.hellogender.hellogender.models.Gender;
import com.hellogender.hellogender.services.GenderRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenderRecognitionController {

    private final GenderRecognitionService genderRecognitionService;

    @GetMapping("/recognizeGender")
    public String recognizeGender(@RequestParam String name, @RequestParam String algorithmVariant) {
        // TODO: Validate algorithm variant?
        return genderRecognitionService.recognizeGender(name, algorithmVariant).name();
    }

    // TODO: Return better validation error message
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
}
