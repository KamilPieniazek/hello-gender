package com.hellogender.hellogender.services;

import com.hellogender.hellogender.gender_recognition.RecognizerProvider;
import com.hellogender.hellogender.gender_recognition.GenderClassifier;
import com.hellogender.hellogender.gender_recognition.recognizers.GenderRecognizer;
import com.hellogender.hellogender.models.Gender;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenderRecognitionService {

    private final GenderClassifier genderClassifier;
    private final RecognizerProvider recognizerProvider;

    public Gender recognizeGender(String name, String algorithmVariant, @Nullable String version) {
        GenderRecognizer genderRecognizer = recognizerProvider.provide(algorithmVariant, version);

        return genderRecognizer.recognize(name);
    }

    public List<String> getTokenList(Gender gender) {
        if (gender.equals(Gender.FEMALE)) {
            return genderClassifier.getFemaleNames();
        } else if (gender.equals(Gender.MALE)) {
            return genderClassifier.getMaleNames();
        } else {
            throw new IllegalArgumentException("Provided gender: " + gender.name() + " Supported genders: MALE or FEMALE");
        }
    }
}
