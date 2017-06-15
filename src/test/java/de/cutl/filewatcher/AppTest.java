package de.cutl.filewatcher;


import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{

    @Test
    public void testInitWithoutConfig() throws IOException {
        String[] args = {};

        try {
            App.main(args);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Konfigurationsdatei fehlt");
        } catch (IOException e) {
            throw e;
        }
    }
}
