package com.hellogender.hellogender.gender_recognition;

import com.hellogender.hellogender.gender_recognition.recognizers.FirstTokenRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.GenderRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.MajorityRuleTokenRecognizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecognizerProvider {
    private final FirstTokenRecognizer firstTokenRecognizer;
    private final MajorityRuleTokenRecognizer majorityRuleTokenRecognizer;

    public GenderRecognizer provide(String algorithmVariant) {
        if (algorithmVariant.equals("1")) {
            return firstTokenRecognizer;
        } else if (algorithmVariant.equals("2")) {
            return majorityRuleTokenRecognizer;
        } else {
            throw new IllegalArgumentException("Wrong variant number");
        }
    }
}
