package de.cutl.filewatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * all file IO.
 */
public class FileCounter {

    /**
     * Counting files in all Subfolders.
     *
     * @param dir
     * @return count of all files
     * @throws IOException
     */
    public int count(File dir) throws IOException {
        final AtomicInteger fileCount = new AtomicInteger();
        Files.list(Paths.get(dir.getAbsolutePath())).forEach((file) -> {
            fileCount.incrementAndGet();
        });
        return fileCount.get();
    }
}
