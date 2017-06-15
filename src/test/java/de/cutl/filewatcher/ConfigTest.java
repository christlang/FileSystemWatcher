package de.cutl.filewatcher;

import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigTest extends TestCase {

    @Rule
    public TemporaryFolder TEST_DIR = new TemporaryFolder(new File(TestConstants.TEST_DIR));

    public static final Path MISSING_CONFIG = Paths.get(TestConstants.TEST_DIR, "missing.prop");
    public static final Path EXAMPLE_CONFIG = Paths.get("example_config.prop");
    public static final Path CONFIG = Paths.get(TestConstants.TEST_DIR, "config.prop");

    private FileCounter cut;

    @Before
    public void setUp() throws IOException {
        TEST_DIR.create();
    }

    @After
    public void tearDown() {
        TEST_DIR.delete();
    }

    @Test
    public void testCreateNotExistingFile() throws IOException {
        try {
            Config cut = new Config(MISSING_CONFIG);
            fail(); // exception should be thrown before
        } catch (Exception e) {
            assertTrue(e.getMessage().startsWith("Konfiguration nicht gefunden:"));
        }
    }

    @Test
    public void testCreateComplete() throws IOException {
        Properties prop = new Properties();
        OutputStream output = null;

        output = new FileOutputStream(CONFIG.toFile());

        // set the properties value
        prop.setProperty("pathToCount", "tmp");
        prop.setProperty("logFile", "test.txt");
        prop.setProperty("limitForMail", "5");
        prop.setProperty("emailUsername", "abc");
        prop.setProperty("emailPassword", "geheim");
        prop.setProperty("emailReceivers", "abc@de.de, def@de.com");
        prop.setProperty("emailSubject", "testSubject");
        prop.setProperty("emailText", "test text\nwith variable %d");

        // save properties to project root folder
        prop.store(output, null);

        output.close();

        Config cut = new Config(CONFIG);
    }

    @Test
    public void testExampleConfig() throws IOException {

        Config cut = new Config(EXAMPLE_CONFIG);
    }

    @Test
    public void testCreateMissingPathToCount() throws IOException {
        Properties prop = new Properties();
        OutputStream output = null;

        output = new FileOutputStream(CONFIG.toFile());

        // set the properties value
        prop.setProperty("logFile", "test.txt");
        prop.setProperty("limitForMail", "5");

        // save properties to project root folder
        prop.store(output, null);

        output.close();

        try {
            Config cut = new Config(CONFIG);
            fail(); // exception should be thrown before
        } catch (Exception e) {
            assertTrue(e.getMessage().startsWith("Konfiguration unvollständig: pathToCount"));
        }
    }

    @Test
    public void testCreateMissingLimitForMail() throws IOException {
        Properties prop = new Properties();
        OutputStream output = null;

        output = new FileOutputStream(CONFIG.toFile());

        // set the properties value
        prop.setProperty("pathToCount", "tmp");
        prop.setProperty("logFile", "test.txt");

        // save properties to project root folder
        prop.store(output, null);

        output.close();

        try {
            Config cut = new Config(CONFIG);
            fail(); // exception should be thrown before
        } catch (Exception e) {
            assertThat(printStackHelper(e), e.getMessage(), CoreMatchers.startsWith("Konfiguration unvollständig: limitForMail"));
        }
    }

    private String printStackHelper(Exception exception) {
        StringBuilder buf = new StringBuilder();
        for(StackTraceElement part : exception.getStackTrace()) {
            buf.append(part.toString());
            buf.append("\n");
        }
        return buf.toString();
    }
}