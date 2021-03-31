package com.hellogender.hellogender.service;

import com.hellogender.hellogender.models.Gender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderRecognitionService {

    // TODO:Extract to separate provider, get from file
    private final List<String> femaleNames = new ArrayList<>();
    private final List<String> maleNames = new ArrayList<>();

    public GenderRecognitionService() {

        femaleNames.add("Maria");
        femaleNames.add("Krystyna");
        femaleNames.add("Anna");
        femaleNames.add("Barbara");
        femaleNames.add("Teresa");
        femaleNames.add("Elżbieta");
        femaleNames.add("Janina");
        femaleNames.add("Zofia");
        femaleNames.add("Jadwiga");
        femaleNames.add("Danuta");
        femaleNames.add("Halina");
        femaleNames.add("Irena");

        maleNames.add("Jan");
        maleNames.add("Stanisław");
        maleNames.add("Andrzej");
        maleNames.add("Józef");
        maleNames.add("Tadeusz");
        maleNames.add("Jerzy");
        maleNames.add("Zbigniew");
        maleNames.add("Krzysztof");
        maleNames.add("Henryk");
        maleNames.add("Ryszard");
    }

    public String recognizeGender(String name, String algorithmVariant) {

        if (algorithmVariant.equals("1")) {
            return variantOne(name);
        } else {
            throw new IllegalArgumentException("Wrong variant number");
        }
    }

    public List<String> getTokenList(Gender gender) {
        if (gender.equals(Gender.MALE)) {
            return maleNames;
        } else if (gender.equals(Gender.FEMALE)) {
            return femaleNames;
        } else {
            throw new IllegalArgumentException("Unrecognized gender provided");
        }
    }

    private String variantOne(String name) {
        String firstToken = name.split(" ")[0];

        if (femaleNames.contains(firstToken)) {
            return "FEMALE";
        } else if (maleNames.contains(firstToken)) {
            return "MALE";
        } else {
            return "INCONCLUSIVE";
        }
    }
}
