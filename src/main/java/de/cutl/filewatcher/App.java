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
        Mailer mailer = new Mailer();

        File logFile = config.getLogFile().toFile();
        int lastCount = ctr.readLastValueFileCount();

        File pathToCount = config.getPathToCount().toFile();
        int fileCount = fc.count(pathToCount);
        ctr.appendToLogFile(LocalDateTime.now(), fileCount);

        int countDiff = lastCount - fileCount;

        System.out.println("Ergebnis geschrieben: " + logFile.getAbsolutePath() + " - neue Dateien: " + countDiff);

        if (countDiff < config.getLimitForMail()) {
            mailer.sendMail(config, countDiff);
            System.out.println("Limit unterschritten -> mail geschickt");
        }
    }
}
