package logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileHelper {

    public static String[][] read(String path) {
        List<String[]> data = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> data.add(s.split("\\s")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new String[0][]);
    }

    public static void write(String content, String path) {
        try {
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeWithAppend(String content, String path) {
        try {
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
