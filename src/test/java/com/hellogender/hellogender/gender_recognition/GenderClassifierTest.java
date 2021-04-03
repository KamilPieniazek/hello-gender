package com.hellogender.hellogender.gender_recognition;

import com.hellogender.hellogender.FileUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenderClassifierTest {
    GenderClassifier genderClassifier;

    @Mock()
    FileUtilities fileUtilitiesMock;

    @BeforeEach
    public void setup() {
        genderClassifier = new GenderClassifier(fileUtilitiesMock);
    }

    @Test
    void isFemale_returnsTrueWhenNameIsInTheList() throws IOException, URISyntaxException {
        String name = "Anna";
        Stream<String> tokens = Stream.of("Anna", "Kamila", "Joanna");

        when(fileUtilitiesMock.getFileContent(anyString())).thenReturn(tokens);


        boolean result = genderClassifier.isFemale(name);

        assertTrue(result);
    }

    @Test
    void isFemale_returnsFalseWhenNameIsNotInTheList() throws IOException, URISyntaxException {
        String name = "Katarzyna";
        Stream<String> tokens = Stream.of("Anna", "Kamila", "Joanna");

        when(fileUtilitiesMock.getFileContent(anyString())).thenReturn(tokens);

        boolean result = genderClassifier.isFemale(name);

        assertFalse(result);
    }

    @Test
    void isFemale_throwsExceptionWhenSomethingBadHappens() throws IOException, URISyntaxException {

        when(fileUtilitiesMock.getFileContent(anyString())).thenThrow();

        boolean result = genderClassifier.isFemale("Anna");

        assertFalse(result);
    }

    @Test
    void isMale_returnsTrueWhenNameIsInTheList() throws IOException, URISyntaxException {
        String name = "Jan";
        Stream<String> tokens = Stream.of("Adam", "Kamil", "Jan");

        when(fileUtilitiesMock.getFileContent(anyString())).thenReturn(tokens);

        boolean result = genderClassifier.isMale(name);

        assertTrue(result);
    }

    @Test
    void isMale_returnsFalseWhenNameIsNotInTheList() throws IOException, URISyntaxException {
        String name = "Karol";
        Stream<String> tokens = Stream.of("Adam", "Kamil", "Jan");

        when(fileUtilitiesMock.getFileContent(anyString())).thenReturn(tokens);

        boolean result = genderClassifier.isMale(name);

        assertFalse(result);
    }

    @Test
    void isMale_throwsExceptionWhenSomethingBadHappens() throws IOException, URISyntaxException {

        when(fileUtilitiesMock.getFileContent(anyString())).thenThrow();

        boolean result = genderClassifier.isMale("Jan");

        assertFalse(result);
    }

    @Test
    void getFemaleNames_getsFemaleNamesList() throws IOException, URISyntaxException {
        List<String> tokens = List.of("Anna", "Kamila", "Joanna");

        when(fileUtilitiesMock.getFileContent(anyString())).thenReturn(tokens.stream());

        List<String> result = genderClassifier.getFemaleNames();

        assertEquals(tokens, result);
    }

    @Test
    void getMaleNames() throws IOException, URISyntaxException {
        List<String> tokens = List.of("Adam", "Kamil", "Jan");

        when(fileUtilitiesMock.getFileContent(anyString())).thenReturn(tokens.stream());

        List<String> result = genderClassifier.getMaleNames();

        assertEquals(tokens, result);
    }

    @Test
    void getMaleNames_getsEmptyNameListWhenSomethingWentWrong() throws IOException, URISyntaxException {
        List<String> tokens = Collections.emptyList();

        when(fileUtilitiesMock.getFileContent(anyString())).thenThrow();

        List<String> result = genderClassifier.getMaleNames();

        assertEquals(tokens, result);
    }
}
