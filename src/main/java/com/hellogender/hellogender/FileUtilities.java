package com.hellogender.hellogender;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Component
public class FileUtilities {
    // TODO: consider handling error in here
    public Stream<String> getFileContent(String pathName){

        InputStream inputStream = getClass().getResourceAsStream(pathName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines();
    }
}

