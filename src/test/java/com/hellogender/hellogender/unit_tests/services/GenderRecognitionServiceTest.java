package com.hellogender.hellogender.unit_tests.services;

import com.hellogender.hellogender.gender_recognition.GenderClassifier;
import com.hellogender.hellogender.gender_recognition.RecognizerProvider;
import com.hellogender.hellogender.gender_recognition.recognizers.GenderRecognizer;
import com.hellogender.hellogender.models.Gender;
import com.hellogender.hellogender.services.GenderRecognitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenderRecognitionServiceTest {

    private GenderRecognitionService genderRecognitionService;

    @Mock() private GenderClassifier genderClassifierMock;
    @Mock() private RecognizerProvider recognizerProviderMock;
    @Mock() private GenderRecognizer genderRecognizerMock;

    @BeforeEach
    public void setup() {
        genderRecognitionService = new GenderRecognitionService(genderClassifierMock, recognizerProviderMock);
    }

    @Test
    void recognizeGender_usesProvidedNameAndVariant() {
        String name = "Kamil";
        String algorithmVariant = "1410";

        when(recognizerProviderMock.provide(algorithmVariant)).thenReturn(genderRecognizerMock);
        when(genderRecognizerMock.recognize(name)).thenReturn(Gender.MALE);

        Gender result = genderRecognitionService.recognizeGender(name, algorithmVariant);

        assertEquals(Gender.MALE, result);
        verify(recognizerProviderMock, times(1)).provide(algorithmVariant);
        verify(genderRecognizerMock, times(1)).recognize(name);
    }

    @Test
    void getTokenList_getsFemaleNames() {
        List<String> names = new ArrayList<>();
        names.add("Ola");
        names.add("Ala");

        when(genderClassifierMock.getFemaleNames()).thenReturn(names);

        List<String> result = genderRecognitionService.getTokenList(Gender.FEMALE);

        assertEquals(names, result);
        verify(genderClassifierMock, times(1)).getFemaleNames();
        verify(genderClassifierMock, times(0)).getMaleNames();
    }

    @Test
    void getTokenList_getsMaleNames() {
        List<String> names = new ArrayList<>();
        names.add("Kamil");
        names.add("Karol");

        when(genderClassifierMock.getMaleNames()).thenReturn(names);

        List<String> result = genderRecognitionService.getTokenList(Gender.MALE);

        assertEquals(names, result);
        verify(genderClassifierMock, times(0)).getFemaleNames();
        verify(genderClassifierMock, times(1)).getMaleNames();
    }

    @Test
    void getTokenList_forUnrecognizedGender_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> genderRecognitionService.getTokenList(Gender.INCONCLUSIVE));

        verify(genderClassifierMock, times(0)).getFemaleNames();
        verify(genderClassifierMock, times(0)).getMaleNames();
    }
}