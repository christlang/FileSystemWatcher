package de.cutl.filewatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Encapsulate the logic to make testing easier.
 *
 */
public class Controller {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    final Config config;
    final FileCounter fc;

    public Controller(Config config, FileCounter fc) throws IOException {

        this.config = config;
        this.fc = fc;
    }

    public int getFileCounte() throws IOException {
        return fc.count(config.getPathToCount().toFile());
    }

    public void appendToLogFile(LocalDateTime timestamp, int fileCount) {
        try(
            FileWriter fw = new FileWriter(config.getLogFile().toFile().getAbsolutePath(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
        ) {
            String timestampString = timestamp.format(formatter);
            out.println(timestampString + " = " + fileCount);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            e.printStackTrace();
        }
    }
}
