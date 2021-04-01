package com.hellogender.hellogender.gender_recognition;

import com.hellogender.hellogender.Configuration;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GenderClassifier {
    public boolean isFemale(String name) {
        return isNameInFile(name, Configuration.femaleTokenPath);
    }

    public boolean isMale(String name) {
        return isNameInFile(name, Configuration.maleTokenPath);
    }

    public List<String> getFemaleNames() {
        return collectNamesFromFile(Configuration.femaleTokenPath);
    }

    public List<String> getMaleNames() {
        return collectNamesFromFile(Configuration.maleTokenPath);
    }

    private boolean isNameInFile(String name, String filePath) {
        Stream<String> lines = null;
        boolean hasMatch = false;

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());
            lines = Files.lines(path);
            hasMatch = lines.anyMatch(line -> line.equalsIgnoreCase(name));
            lines.close();
        } catch (Exception e) {
            // TODO: Better exception hadler
            System.out.println(e);
        } finally {
            if (lines != null) {
                lines.close();
            }
        }

        return hasMatch;
    }

    private List<String> collectNamesFromFile(String filePath) {
        Stream<String> lines = null;
        List<String> names = Collections.emptyList();

        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());
            lines = Files.lines(path);
            names = lines.collect(Collectors.toList());
            lines.close();
        } catch (Exception e) {
            // TODO: Better exception hadler
            System.out.println(e);
        } finally {
            if (lines != null) {
                lines.close();
            }
        }

        return names;
    }
}
