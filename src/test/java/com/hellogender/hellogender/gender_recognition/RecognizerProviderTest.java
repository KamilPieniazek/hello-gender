package com.hellogender.hellogender.gender_recognition;

import com.hellogender.hellogender.gender_recognition.recognizers.FirstTokenRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.GenderRecognizer;
import com.hellogender.hellogender.gender_recognition.recognizers.MajorityRuleTokenRecognizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RecognizerProviderTest {

    RecognizerProvider recognizerProvider;

    @Mock()
    FirstTokenRecognizer firstTokenRecognizerMock;
    @Mock()
    MajorityRuleTokenRecognizer majorityRuleTokenRecognizerMock;

    @BeforeEach
    public void setup() {
        recognizerProvider = new RecognizerProvider(firstTokenRecognizerMock, majorityRuleTokenRecognizerMock);
    }

    @Test
    void provide_getsFirstTokenRecognizer() {
        String algorithmVariant = "1";

        GenderRecognizer result = recognizerProvider.provide(algorithmVariant);

        assertEquals(firstTokenRecognizerMock, result);
    }

    @Test
    void provide_getsMajorityRuleTokenRecognizer() {
        String algorithmVariant = "2";

        GenderRecognizer result = recognizerProvider.provide(algorithmVariant);

        assertEquals(majorityRuleTokenRecognizerMock, result);
    }

    @Test
    void provide_forUnrecognizedRecognizer_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> recognizerProvider.provide("Non existing variant")
        );
    }
}