package de.cutl.filewatcher;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControllerTest extends TestCase {

    @Test
    public void testAppendToLogFileEmpty() throws IOException {
        Path exampleLog = Paths.get("target", "example.log");
        exampleLog.toFile().delete(); // delete previous files if not done right.
        exampleLog.toFile().deleteOnExit();

        Config config = mock(Config.class);
        when(config.getLogFile()).thenReturn(exampleLog);
        Controller cut = new Controller(config, null);

        LocalDateTime now = LocalDateTime.parse("15.06.2017 11:13:50", Controller.formatter);

        cut.appendToLogFile(now,2);

        String content = new String(Files.readAllBytes(exampleLog));

        assertEquals("15.06.2017 11:13:50 = 2\n", content);


    }
}
