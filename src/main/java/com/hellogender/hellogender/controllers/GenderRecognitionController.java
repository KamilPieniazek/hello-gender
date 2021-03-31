package com.hellogender.hellogender.controllers;

import com.hellogender.hellogender.service.GenderRecognitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenderRecognitionController {

    private final GenderRecognitionService genderRecognitionService;

    @GetMapping("/recognizeGender")
    public String recognizeGender(@RequestParam String name, @RequestParam String algorithmVariant) {
        // TODO: Validate algorithm variant?
        return genderRecognitionService.recognizeGender(name, algorithmVariant);
    }
}
