package celestial.data;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataLoader {

    public static Map<String, Object> loadData(Path dataPath) throws IOException {
        Map<String, Object> objects = new LinkedHashMap<>();

        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dataPath)) {
            for (Path path : ds) {
                if (!Files.isDirectory(path)) continue;
                loadObjects(objects, path, yaml);
            }
        }

        return objects;
    }

    private static void loadObjects(Map<String, Object> objects, Path path, Yaml yaml) throws IOException {
        Path objectsPath = path.resolve("objects");

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(objectsPath)) {
            for (Path objectFile : ds) {
                if (Files.isDirectory(objectFile)) continue;
                loadObjectFile(objects, objectFile, yaml);
            }
        }
    }

    private static void loadObjectFile(Map<String, Object> objects, Path path, Yaml yaml) throws IOException {
        // Check Buffered InputStream
        InputStream inputStream = Files.newInputStream(path);

        Map<String, Object> currentObjects = yaml.load(inputStream);
        String prefix = getFileNameWithoutExtension(path);
        for (Map.Entry<String, Object> e : currentObjects.entrySet()) {
            String key = prefix + "." + e.getKey();
            Object value = e.getValue();
            objects.put(key, value);
        }
    }

    private static String getFileNameWithoutExtension(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.length() - ".yml".length());
    }
}
