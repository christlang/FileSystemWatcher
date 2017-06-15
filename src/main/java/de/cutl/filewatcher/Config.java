package de.cutl.filewatcher;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    Properties prop = new Properties();
    InputStream input = null;

    private final Path pathToCount;
    private final Path logFile;
    private final int limitForMail;

    public Config(Path propertyFile) throws IOException {
        File configFile = propertyFile.toFile();
        if (!configFile.exists()) {
            throw new RuntimeException("Konfiguration nicht gefunden: " + configFile.getAbsolutePath());
        }
        input = new FileInputStream(configFile.getAbsolutePath());

        prop.load(input);

        pathToCount = readPropertyPath("pathToCount", "Konfiguration unvollständig: pathToCount fehlt in " + configFile.getAbsolutePath());
        logFile = readPropertyPath("logFile", "Konfiguration unvollständig: logFile fehlt in " + configFile.getAbsolutePath());
        limitForMail = readPropertyInt("limitForMail", "Konfiguration unvollständig: limitForMail fehlt in " + configFile.getAbsolutePath());
    }

    private Path readPropertyPath(String propertyName, String errorMessage) {
        String propValue = prop.getProperty(propertyName);
        if (propValue == null) {
            throw new RuntimeException(errorMessage);
        }
        final Path path = Paths.get(propValue);
        return path;
    }

    private int readPropertyInt(String propertyName, String errorMessage) {
        String propValue = prop.getProperty(propertyName);
        if (propValue == null) {
            throw new RuntimeException(errorMessage);
        }
        final int integer = Integer.parseInt(propValue);
        return integer;
    }

    public Path getPathToCount() {
        return pathToCount;
    }

    public Path getLogFile() {
        return logFile;
    }

    public int getLimitForMail() {
        return limitForMail;
    }
}
