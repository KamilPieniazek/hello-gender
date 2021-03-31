package com.hellogender.hellogender.gender_recognition.recognizers;

import com.hellogender.hellogender.gender_recognition.GenderClassifier;
import com.hellogender.hellogender.models.Gender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MajorityRuleTokenRecognizer implements GenderRecognizer {
    private final GenderClassifier genderClassifier;

    @Override
    public Gender recognize(String name) {
        return recognizeGenderByMajorityRule(name);
    }

    private Gender recognizeGenderByMajorityRule(String name) {
        int femaleNamesCount = 0;
        int maleNamesCount = 0;
        String[] nameTokens = name.split(" ");

        for (String nameToken : nameTokens) {
            if (genderClassifier.isFemale(nameToken)) {
                femaleNamesCount++;
            } else if (genderClassifier.isMale(nameToken)) {
                maleNamesCount++;
            }
        }

        if (femaleNamesCount > maleNamesCount) {
            return Gender.FEMALE;
        }
        if (maleNamesCount > femaleNamesCount) {
            return Gender.MALE;
        }

        return Gender.INCONCLUSIVE;
    }
}
