package de.cutl.filewatcher;

import java.io.*;
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

    public int readLastValueFileCount() {
        BufferedReader br = null;
        String lastLine = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(config.getLogFile().toFile()));

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                lastLine=sCurrentLine;
            }
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }

        if (lastLine == null) {
            return 0;
        }
        return Integer.parseInt(lastLine.split("=")[1].trim());
    }

    public void writeMailIfLimitIsReached(int countDiff, Mailer mailer) {

        if (countDiff < config.getLimitForMail()) {
            mailer.sendMail(config, countDiff);
            System.out.println("Limit unterschritten -> mail geschickt");
        }
    }
}
