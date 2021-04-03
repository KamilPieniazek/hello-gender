package com.hellogender.hellogender;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class FileUtilities {
  // TODO: consider handling error in here
    public Stream<String> getFileContent(String pathName)
            throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource(pathName).toURI());
        return Files.lines(path);
    }
}

