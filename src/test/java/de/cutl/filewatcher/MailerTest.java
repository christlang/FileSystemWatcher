package de.cutl.filewatcher;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;


public class MailerTest extends TestCase {

    public void testEmpty() {
        // do nothing
    }

    @Test
    public void _testEmail() throws IOException {
        Mailer cut = new Mailer();

        Config config = new Config(Paths.get("example_config.prop"));

        cut.sendMail(config, 10);
    }
}
