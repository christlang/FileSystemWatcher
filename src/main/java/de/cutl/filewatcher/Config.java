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
    private final String emailUsername;
    private final String emailPassword;
    private final String emailReceivers;
    private final String emailSubject;
    private final String emailText;

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

        emailUsername = readPropertyString("emailUsername",  "Konfiguration unvollständig: emailUsername fehlt in " + configFile.getAbsolutePath());
        emailPassword = readPropertyString("emailPassword",  "Konfiguration unvollständig: emailPassword fehlt in " + configFile.getAbsolutePath());
        emailReceivers = readPropertyString("emailReceivers",  "Konfiguration unvollständig: emailReceivers fehlt in " + configFile.getAbsolutePath());
        emailSubject = readPropertyString("emailSubject",  "Konfiguration unvollständig: emailSubject fehlt in " + configFile.getAbsolutePath());
        emailText = readPropertyString("emailText",  "Konfiguration unvollständig: emailText fehlt in " + configFile.getAbsolutePath());
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

    private String readPropertyString(String propertyName, String errorMessage) {
        String propValue = prop.getProperty(propertyName);
        if (propValue == null || propValue.isEmpty()) {
            throw new RuntimeException(errorMessage);
        }
        return propValue;
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

    public String getEmailUsername() {
        return emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public String getEmailReceivers() {
        return emailReceivers;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getEmailText() {
        return emailText;
    }
}
