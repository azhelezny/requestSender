package rules.classes;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author Andrey Zhelezny
 *         Date: 4/11/18
 */
public enum CollectFrom {
    RESPONSE_HEADERS("RESPONSE_HEADERS"),
    RESPONSE_BODY("RESPONSE_BODY"),
    VALUE("VALUE");

    private String value;

    CollectFrom(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static CollectFrom getEnumFromValue(String value) {
        for (CollectFrom collectFrom : CollectFrom.values())
            if (collectFrom.value.equals(value))
                return collectFrom;
        StringBuilder sb = new StringBuilder("Unable to find value [");
        sb.append(value)
                .append("] for enum [")
                .append(CollectFrom.class.getSimpleName())
                .append("] available values: [")
                .append(Arrays.toString(CollectFrom.values()));

        JOptionPane.showMessageDialog(null, sb.toString());
        throw new RuntimeException();
    }
}
