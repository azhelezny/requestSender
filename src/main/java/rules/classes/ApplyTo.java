package rules.classes;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author Andrey Zhelezny
 *         Date: 4/11/18
 */
public enum ApplyTo {
    REQUEST_HEADERS("RESPONSE_HEADERS"),
    REQUEST_BODY("RESPONSE_BODY"),
    REQUEST_URL("REQUEST_URL");

    private String value;

    ApplyTo(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static ApplyTo getEnumFromValue(String value) {
        for (ApplyTo collectFrom : ApplyTo.values())
            if (collectFrom.value.equals(value))
                return collectFrom;
        StringBuilder sb = new StringBuilder("Unable to find value [");
        sb.append(value)
                .append("] for enum [")
                .append(CollectFrom.class.getSimpleName())
                .append("] available values: [")
                .append(Arrays.toString(ApplyTo.values()));

        JOptionPane.showMessageDialog(null, sb.toString());
        throw new RuntimeException();
    }
}
