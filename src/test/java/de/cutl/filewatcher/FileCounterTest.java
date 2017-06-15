package de.cutl.filewatcher;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCounterTest extends TestCase {

    private Path TEST_DIR;

    @Before
    public void setUp() throws IOException {
        TEST_DIR = FileSystems.getDefault().getPath("target", "randome");

        if (Files.exists(TEST_DIR)) {
            Files.delete(TEST_DIR);
        }
        Files.createDirectory(TEST_DIR);
    }

    @Test
    public void testFileCountingInPath() throws IOException {


    }
}