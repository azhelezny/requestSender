package utils;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextUtils {
    public static String getFromGroupIfPresented(String expression, int groupNumber, String text) {
        Pattern regex = Pattern.compile(expression, Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher regexMatcher = regex.matcher(text);
        try {
            return (regexMatcher.find()) ? regexMatcher.group(groupNumber) : null;
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "wrong group in regular expression [" + expression + "]");
            throw new RuntimeException("wrong group in regular expression [" + expression + "]");
        }
    }

    public static boolean isHappensInText(String expression, String text) {
        try {
            Pattern regex = Pattern.compile(expression, Pattern.MULTILINE | Pattern.COMMENTS);
            Matcher regexMatcher = regex.matcher(text);
            return regexMatcher.find();
        } catch (PatternSyntaxException ex) {
            JOptionPane.showMessageDialog(null, "");
            return false;
        }
    }

    public static String replaceInText(String expression, String newValue, final String text) {
        return text.replaceFirst(expression, newValue);
    }
}