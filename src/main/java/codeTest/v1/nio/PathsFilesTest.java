package codeTest.v1.nio;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsFilesTest {
    public static void main(String[] args) {
        // Absolute path: "c:\\data\\myfile.txt"
        // Relative path:"/home/jakobjenkov/myfile.txt"
        Path path = Paths.get("");

        File file = path.toFile();
        path = file.toPath();
    }
}
