package com.hellogender.hellogender.integration_tests;

import com.google.gson.Gson;
import com.hellogender.hellogender.Configuration;
import com.hellogender.hellogender.HelloGenderApplication;
import com.hellogender.hellogender.models.ErrorModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HelloGenderApplication.class
)
@AutoConfigureMockMvc
class GenderRecognitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("provideParametersForAlgorithmOne")
    void recognizeGender_whenUsingAlgorithmOne_returnsCorrectGender(String name, String gender) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recognizeGender")
                .queryParam("name", name)
                .queryParam("algorithmVariant", "1");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        assertEquals(gender, responseContent);
    }

    @Test
    void recognizeGender_whenProvidedAlgorithmVersion_returnsGender() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recognizeGender")
                .queryParam("name", "Kamil")
                .queryParam("algorithmVariant", "1")
                .queryParam("version", "0");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        assertEquals("MALE", responseContent);

    }

    @Test
    void recognizeGender_whenProvidedWrongAlgorithmVersion_returnsError() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recognizeGender")
                .queryParam("name", "Kamil")
                .queryParam("algorithmVariant", "1")
                .header(Configuration.versionHeader, "10");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Gson gson = new Gson();
        ErrorModel errorModel = gson.fromJson(responseContent, ErrorModel.class);

        assertEquals("Wrong variant and version combination provided", errorModel.getMessage());
        assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void recognizeGender_whenUsingNonExistingAlgorithm_returnsError() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recognizeGender")
                .queryParam("name", "Kamil")
                .queryParam("algorithmVariant", "10");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Gson gson = new Gson();
        ErrorModel errorModel = gson.fromJson(responseContent, ErrorModel.class);

        assertEquals("Wrong variant and version combination provided", errorModel.getMessage());
        assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void recognizeGender_whenUsingBlankName_returnsError() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recognizeGender")
                .queryParam("name", " ")
                .queryParam("algorithmVariant", "1");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Gson gson = new Gson();
        ErrorModel errorModel = gson.fromJson(responseContent, ErrorModel.class);

        assertEquals("recognizeGender.name: Name must not be blank", errorModel.getMessage());
        assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
    }


    @ParameterizedTest
    @MethodSource("provideParametersForAlgorithmTwo")
    void recognizeGender_whenUsingAlgorithmTwo_returnsCorrectGender(String name, String gender) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/recognizeGender")
                .queryParam("name", name)
                .queryParam("algorithmVariant", "2");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        assertEquals(gender, responseContent);
    }

    @Test
    void getTokenList_whenFemale_returnFemaleNameList() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tokenList")
                .queryParam("gender", "FEMALE");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString();

        Gson gson = new Gson();
        List<String> names = gson.fromJson(responseContent, ArrayList.class);

        assertTrue(names.size() > 0);
        assertTrue(names.contains("Anna"));
        assertTrue(names.contains("Maria"));
    }

    @Test
    void getTokenList_whenMale_returnMaleNameList() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tokenList")
                .queryParam("gender", "MALE");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String responseContent = result.getResponse().getContentAsString(Charset.defaultCharset());

        Gson gson = new Gson();
        List<String> names = gson.fromJson(responseContent, ArrayList.class);

        assertTrue(names.size() > 0);
        assertTrue(names.contains("Kamil"));
        assertTrue(names.contains("Henryk"));
        assertTrue(names.contains("Pawe??"));
    }

    private static Stream<Arguments> provideParametersForAlgorithmOne() {
        return Stream.of(
                Arguments.of("Kamil", "MALE"),
                Arguments.of("Kamil Anna Maria", "MALE"),
                Arguments.of("Anna", "FEMALE"),
                Arguments.of("Anna Kamil Henryk", "FEMALE"),
                Arguments.of("XXX", "INCONCLUSIVE")
        );
    }

    private static Stream<Arguments> provideParametersForAlgorithmTwo() {
        return Stream.of(
                Arguments.of("Kamil Pawe?? Pieni????ek", "MALE"),
                Arguments.of("Kamil Maria Pawe?? Pieni????ek", "MALE"),
                Arguments.of("Kamil Maria Pieni????ek", "INCONCLUSIVE"),
                Arguments.of("Anna Maria Jopek", "FEMALE"),
                Arguments.of("Anna Maria Henryk Jopek", "FEMALE")
        );
    }
}