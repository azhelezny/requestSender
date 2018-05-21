package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Zhelezny
 *         Date: 3/5/18
 */
public class PathUtils {
    public static String getOsPath(String path, String... pathFragments) {
        String separator = File.separator;
        StringBuilder result = new StringBuilder();
        result.append((separator.equals("\\")) ? path.replace("/", "\\") : path.replace("\\", "/"));
        for (String pathFragment : pathFragments)
            result.append(separator).append(pathFragment);
        return result.toString();
    }

    public static List<String> getFolderPaths(String path) throws IOException {
        List<String> result = new ArrayList<>();
        Files.list(Paths.get(path)).filter(Files::isDirectory).forEach(filePath -> result.add(filePath.toString()));
        return result;
    }

    public static List<String> getFolderNames(String path) throws IOException {
        List<String> result = new ArrayList<>();
        File[] folderContent = new File(path).listFiles();
        if (folderContent == null)
            return result;
        for (File file : folderContent)
            if (file.isDirectory())
                result.add(file.getName());
        return result;
    }
}
