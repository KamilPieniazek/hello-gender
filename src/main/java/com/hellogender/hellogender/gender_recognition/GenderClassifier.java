package com.hellogender.hellogender.gender_recognition;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderClassifier {
    // TODO: Get from file
    private final List<String> femaleNames = new ArrayList<>();
    private final List<String> maleNames = new ArrayList<>();

    public GenderClassifier() {
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

    // TODO: Handle lower cases
    public boolean isMale(String name) {
        return maleNames.contains(name);
    }

    public boolean isFemale(String name) {
        return femaleNames.contains(name);
    }

    public List<String> getFemaleNames() {
        return femaleNames;
    }

    public List<String> getMaleNames() {
        return maleNames;
    }
}
