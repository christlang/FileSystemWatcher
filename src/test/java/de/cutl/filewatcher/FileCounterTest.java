package de.cutl.filewatcher;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCounterTest extends TestCase {



    @Rule
    public TemporaryFolder TEST_DIR = new TemporaryFolder(new File("target"));

    private FileCounter cut;

    @Before
    public void setUp() throws IOException {
        TEST_DIR.create();

        cut = new FileCounter();
    }

    @After
    public void tearDown() {
        //TEST_DIR.delete();
    }


    @Test
    public void testEmptyDirectory() throws IOException {
        int result = cut.count(TEST_DIR.getRoot());

        assertEquals(0, result);
    }

    @Test
    public void testDirectoryOneFile() throws IOException {
        TEST_DIR.newFile("test.txt");

        assertEquals(1, cut.count(TEST_DIR.getRoot()));
    }

    @Test
    public void testDirectoryTwoFile() throws IOException {
        TEST_DIR.newFile("test1.txt");
        TEST_DIR.newFile("test2.txt");

        assertEquals(2, cut.count(TEST_DIR.getRoot()));
    }

    @Test
    public void testDirectoryOneFileInSubdir() throws IOException {
        Path subDir = Files.createDirectory(Paths.get(TEST_DIR.getRoot().getAbsolutePath(), "subdir"));

        Files.createFile(Paths.get(subDir.toString(), "test.txt"));

        assertEquals(1, cut.count(TEST_DIR.getRoot()));
    }

    @Test
    public void testDirectoryOneFileInSubdirAndOneFile() throws IOException {
        Path subDir = Files.createDirectory(Paths.get(TEST_DIR.getRoot().getAbsolutePath(), "subdir"));

        Files.createFile(Paths.get(subDir.toString(), "test.txt"));
        TEST_DIR.newFile("test1.txt");

        assertEquals(2, cut.count(TEST_DIR.getRoot()));
    }
}