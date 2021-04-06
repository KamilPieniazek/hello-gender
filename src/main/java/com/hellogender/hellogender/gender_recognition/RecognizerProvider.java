package com.hellogender.hellogender.gender_recognition;

import com.hellogender.hellogender.gender_recognition.recognizers.FirstTokenRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.GenderRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.MajorityRuleTokenRecognizer;
import org.javatuples.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RecognizerProvider {

    private final Map<Pair<String, String>, GenderRecognizer> variants = new HashMap<>();

    public RecognizerProvider(FirstTokenRecognizer firstTokenRecognizer, MajorityRuleTokenRecognizer majorityRuleTokenRecognizer) {
        variants.put(new Pair<>("1", "0"), firstTokenRecognizer);
        variants.put(new Pair<>("1", "1"), firstTokenRecognizer);
        variants.put(new Pair<>("2", "0"), majorityRuleTokenRecognizer);
    }

    public GenderRecognizer provide(String algorithmVariant, @Nullable String version) {
        if (version == null) {
            version = "0";
        }

        Pair<String, String> variantVersion = new Pair<>(algorithmVariant, version);
        if (!variants.containsKey(variantVersion)) {
            throw new IllegalArgumentException("Wrong variant and version combination provided");
        }

        return variants.get(variantVersion);
    }
}
