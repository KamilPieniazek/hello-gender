package com.hellogender.hellogender.unit_tests.gender_recognition.recognizers;

import com.hellogender.hellogender.gender_recognition.GenderClassifier;
import com.hellogender.hellogender.gender_recognition.recognizers.MajorityRuleTokenRecognizer;
import com.hellogender.hellogender.models.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MajorityRuleTokenRecognizerTest {
    @Mock()
    private GenderClassifier genderClassifierMock;
    MajorityRuleTokenRecognizer majorityRuleTokenRecognizer;

    @BeforeEach
    public void setup() {
        majorityRuleTokenRecognizer = new MajorityRuleTokenRecognizer(genderClassifierMock);
    }

    @Test
    void recognize_getsFemale() {
        String name = "Jan Maria Rokita";

        when(genderClassifierMock.isFemale(anyString())).thenReturn(true);

        Gender result = majorityRuleTokenRecognizer.recognize(name);

        assertEquals(Gender.FEMALE, result);
        verify(genderClassifierMock, times(3)).isFemale(anyString());
        verify(genderClassifierMock, times(0)).isMale(anyString());

    }

    @Test
    void recognize_getsMale() {
        String name = "Jan Maria Rokita";

        when(genderClassifierMock.isMale(anyString())).thenReturn(true);

        Gender result = majorityRuleTokenRecognizer.recognize(name);

        assertEquals(Gender.MALE, result);
        verify(genderClassifierMock, times(3)).isMale(anyString());
        verify(genderClassifierMock, times(3)).isFemale(anyString());
    }

    @Test
    void recognize_getsInconclusive() {
        String name = "Jan Maria Rokita";

        when(genderClassifierMock.isFemale(anyString())).thenReturn(false);
        when(genderClassifierMock.isMale(anyString())).thenReturn(false);

        Gender result = majorityRuleTokenRecognizer.recognize(name);

        assertEquals(Gender.INCONCLUSIVE, result);
        verify(genderClassifierMock, times(3)).isFemale(anyString());
        verify(genderClassifierMock, times(3)).isMale(anyString());
    }
}