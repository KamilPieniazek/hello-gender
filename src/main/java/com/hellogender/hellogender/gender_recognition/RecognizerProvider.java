package com.hellogender.hellogender.gender_recognition;

import com.hellogender.hellogender.gender_recognition.recognizers.FirstTokenRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.GenderRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.MajorityRuleTokenRecognizer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RecognizerProvider {

    private final Map<String, GenderRecognizer> variants = new HashMap<>();

    public RecognizerProvider(FirstTokenRecognizer firstTokenRecognizer, MajorityRuleTokenRecognizer majorityRuleTokenRecognizer) {
        variants.put("1", firstTokenRecognizer);
        variants.put("2", majorityRuleTokenRecognizer);
    }

    public GenderRecognizer provide(String algorithmVariant) {
        if (!variants.containsKey(algorithmVariant)) {
            throw new IllegalArgumentException("Wrong variant number");
        }

        return variants.get(algorithmVariant);
    }
}
