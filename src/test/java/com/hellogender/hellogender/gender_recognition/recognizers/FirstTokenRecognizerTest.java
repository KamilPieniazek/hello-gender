package com.hellogender.hellogender.gender_recognition.recognizers;

import com.hellogender.hellogender.gender_recognition.GenderClassifier;
import com.hellogender.hellogender.models.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirstTokenRecognizerTest {

    @Mock()
    private GenderClassifier genderClassifierMock;
    FirstTokenRecognizer firstTokenRecognizer;

    @BeforeEach
    public void setup() {
        firstTokenRecognizer = new FirstTokenRecognizer(genderClassifierMock);
    }

    @Test
    void recognize_gesFemale() {
        String name="Kamil Paweł Pieniążek";

        when(genderClassifierMock.isFemale(anyString())).thenReturn(true);
        Gender result = firstTokenRecognizer.recognize(name);

        assertEquals(Gender.FEMALE,result);
        verify(genderClassifierMock,times(1)).isFemale("Kamil");
        verify(genderClassifierMock,times(0)).isMale("Kamil");
    }

    @Test
    void recognize_getsMale() {
        String name = "Kamil Pawel Pieniążek";

        when(genderClassifierMock.isMale(anyString())).thenReturn(true);

        Gender result = firstTokenRecognizer.recognize(name);

        assertEquals(Gender.MALE, result);
        verify(genderClassifierMock, times(1)).isFemale("Kamil");
        verify(genderClassifierMock, times(1)).isMale("Kamil");
    }

    @Test
    void recognize_getsInconclusive() {
        String name = "Kamil Paweł Pieniążek";

        when(genderClassifierMock.isFemale(anyString())).thenReturn(false);
        when(genderClassifierMock.isMale(anyString())).thenReturn(false);

        Gender result = firstTokenRecognizer.recognize(name);

        assertEquals(Gender.INCONCLUSIVE, result);
        verify(genderClassifierMock, times(1)).isFemale("Kamil");
        verify(genderClassifierMock, times(1)).isMale("Kamil");

    }
}