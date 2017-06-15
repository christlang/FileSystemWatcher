package de.cutl.filewatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("Konfigurationsdatei fehlt");
        }
        Config config = new Config(Paths.get(args[0]));
        FileCounter fc = new FileCounter();
        Controller ctr = new Controller(config, fc);

        File logFile = config.getLogFile().toFile();
        File pathToCount = config.getPathToCount().toFile();
        int fileCount = fc.count(pathToCount);
        ctr.appendToLogFile(LocalDateTime.now(), fileCount);

        System.out.println("Ergebnis geschrieben: " + logFile.getAbsolutePath());
    }
}
