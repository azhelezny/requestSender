package utils;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonUtils {
    public static byte[] readBinaryFile(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, String.format("Unable to read file content [%s]: %s", filePath, e.getMessage()));
            return null;
        }
    }

    public static String readFileToString(String filePath) {
        try {
            byte[] encoded = readBinaryFile(filePath);
            if (encoded != null)
                return new String(encoded, "UTF-8");
            else return null;
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, String.format("Unable to read file content [%s]: %s", filePath, e.getMessage()));
        }
        return null;
    }

    public static boolean storeBinaryToFile(byte[] binary, String fileName) {
        if (binary == null || binary.length <= 0) {
            JOptionPane.showMessageDialog(null, "Empty binary data");
            return false;
        }
        try {
            OutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            stream.write(binary);
            stream.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, exceptionAsString(e));
            return false;
        }
        return false;
    }

    public static String exceptionAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}