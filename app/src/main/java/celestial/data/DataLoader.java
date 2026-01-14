package celestial.data;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class DataLoader {

    public static Data loadData(Path dataPath) throws IOException {
        Data data = new Data();

        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dataPath)) {
            for (Path path : ds) {
                if (!Files.isDirectory(path)) continue;
                loadDir(data.getObjects(), path.resolve("objects"), yaml);
                loadDir(data.getSystems(), path.resolve("systems"), yaml);
            }
        }

        return data;
    }

    private static void loadDir(Map<String, Object> objects, Path path, Yaml yaml) throws IOException {

        if (!Files.exists(path)) return;

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            for (Path objectFile : ds) {
                if (Files.isDirectory(objectFile)) continue;
                loadFile(objects, objectFile, yaml);
            }
        }
    }

    private static void loadFile(Map<String, Object> objects, Path path, Yaml yaml) throws IOException {
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
