package de.cutl.filewatcher;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ControllerTest extends TestCase {

    private Path exampleLog;
    private Controller cut;
    private Config config;

    private static final int MAIL_LIMIT = 5;

    @Before
    public void setUp() throws IOException {
        exampleLog = Paths.get("target", "example.log");
        exampleLog.toFile().delete(); // delete previous files if not done right.
        exampleLog.toFile().deleteOnExit();

        config = mock(Config.class);
        when(config.getLogFile()).thenReturn(exampleLog);
        when(config.getLimitForMail()).thenReturn(MAIL_LIMIT);
        cut = new Controller(config, null);
    }


    @Test
    public void testAppendToLogFileEmpty() throws IOException {
        LocalDateTime now = LocalDateTime.parse("15.06.2017 11:13:50", Controller.formatter);

        cut.appendToLogFile(now,2);

        String content = new String(Files.readAllBytes(exampleLog));

        assertEquals("15.06.2017 11:13:50 = 2\n", content);
    }

    @Test
    public void testAppendToLogFile() throws IOException {
        LocalDateTime now1 = LocalDateTime.parse("15.06.2017 11:13:50", Controller.formatter);
        LocalDateTime now2 = LocalDateTime.parse("15.06.2017 11:13:59", Controller.formatter);

        cut.appendToLogFile(now1,2);
        cut.appendToLogFile(now2,3);

        String content = new String(Files.readAllBytes(exampleLog));

        assertEquals("15.06.2017 11:13:50 = 2\n" +
                "15.06.2017 11:13:59 = 3\n", content);
    }

    @Test
    public void testReadLastValueFileCountNoLine() {
        int count = cut.readLastValueFileCount();

        assertEquals(0, count);
    }

    @Test
    public void testReadLastValueFileCountSingleLine() {
        LocalDateTime now = LocalDateTime.parse("15.06.2017 11:13:50", Controller.formatter);

        cut.appendToLogFile(now,2);

        int count = cut.readLastValueFileCount();

        assertEquals(2, count);
    }

    @Test
    public void testReadLastValueFileCountMultipleLines() {
        LocalDateTime now = LocalDateTime.parse("15.06.2017 11:13:50", Controller.formatter);

        cut.appendToLogFile(now,2);
        cut.appendToLogFile(now,3);

        int count = cut.readLastValueFileCount();

        assertEquals(3, count);
    }

    @Test
    public void testWriteMailIfLimitIsReachedLimitNotReached() {
        int counter = MAIL_LIMIT + 1;

        Mailer mailer = mock(Mailer.class);

        cut.writeMailIfLimitIsReached(counter, mailer);

        verify(mailer, times(0)).sendMail(config, counter);
    }

    @Test
    public void testWriteMailIfLimitIsReachedLimitReached() {
        int counter = MAIL_LIMIT - 1;

        Mailer mailer = mock(Mailer.class);

        cut.writeMailIfLimitIsReached(counter, mailer);

        verify(mailer, times(1)).sendMail(config, counter);
    }
}

