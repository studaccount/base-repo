package ro.uaic.info.repohub.github_communication.utils;

import lombok.extern.log4j.Log4j2;
import ro.uaic.info.repohub.github_communication.exceptions.FileWriteException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

@Log4j2
public class FileUtils {
    public static void writeToFile(String content, String path) {
        try {
            if (content == null) {
                log.error("Content for " + path + " is null");
                return;
            }
            Files.createDirectories(Paths.get(path).getParent());
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            log.error("Error writing to file: " + e.getMessage());
            throw new FileWriteException(e.getMessage());
        }
    }

    public static void deleteFolder(String folder){
        try {
        Files.walkFileTree(Paths.get(folder), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw exc;
                }
            }
        });
        }
        catch (IOException e) {
            log.error("Error deleting folder: " + e.getMessage());
            throw new FileWriteException(e.getMessage());
        }
    }
}
