package com.hellogender.hellogender.gender_recognition.recognizers;

import com.hellogender.hellogender.gender_recognition.GenderClassifier;
import com.hellogender.hellogender.models.Gender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FirstTokenRecognizer implements GenderRecognizer {
    private final GenderClassifier genderClassifier;

    @Override
    public Gender recognize(String name) {
        return recognizeGenderByFirstToken(name);
    }

    private Gender recognizeGenderByFirstToken(String name) {
        String firstToken = name.split(" ")[0];

        if (genderClassifier.isFemale(firstToken)) {
            return Gender.FEMALE;
        } else if (genderClassifier.isMale(firstToken)) {
            return Gender.MALE;
        } else {
            return Gender.INCONCLUSIVE;
        }
    }
}
