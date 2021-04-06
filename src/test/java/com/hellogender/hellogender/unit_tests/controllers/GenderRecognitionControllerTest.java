package com.hellogender.hellogender.unit_tests.controllers;

import com.hellogender.hellogender.controllers.GenderRecognitionController;
import com.hellogender.hellogender.models.Gender;
import com.hellogender.hellogender.services.GenderRecognitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenderRecognitionControllerTest {

    @Mock()
    GenderRecognitionService genderRecognitionServiceMock;

    GenderRecognitionController genderRecognitionController;

    @BeforeEach
    public void setup() {
        genderRecognitionController = new GenderRecognitionController(genderRecognitionServiceMock);
    }

    @Test
    void recognizeGender() {
        String name = "Jan Maria Rokita";
        String algorithmVariant = "1";
        String version = "0";

        when(genderRecognitionServiceMock.recognizeGender(anyString(),anyString(), anyString())).thenReturn(Gender.MALE);

        String result = genderRecognitionController.recognizeGender(name, algorithmVariant,version);

        assertEquals(Gender.MALE.name(), result);
        verify(genderRecognitionServiceMock, times(1)).recognizeGender(name, algorithmVariant,version);
    }

    @Test
    void getTokenList() {
        Gender gender = Gender.INCONCLUSIVE;
        List<String> names = Collections.emptyList();

        when(genderRecognitionServiceMock.getTokenList(any(Gender.class))).thenReturn(names);

        List<String> results = genderRecognitionController.getTokenList(gender);

        assertEquals(names, results);
    }
}