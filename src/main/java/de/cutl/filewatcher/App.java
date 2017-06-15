package de.cutl.filewatcher;

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

        int fileCount = fc.count(config.getPathToCount().toFile());
        ctr.appendToLogFile(LocalDateTime.now(), fileCount);
    }
}
