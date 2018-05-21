package rest.enums;

import javax.swing.*;

/**
 * @author Andrey Zhelezny
 *         Date: 8/31/15
 */
public enum RestMethod {
    GET("GET"),
    PUT("PUT"),
    POST("POST"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS");

    private String methodName;

    RestMethod(String name) {
        this.methodName = name;
    }

    public String toString() {
        return this.methodName;
    }

    public static RestMethod get(String value) {
        for (RestMethod method : RestMethod.values())
            if (method.methodName.toLowerCase().equals(value.toLowerCase()))
                return method;
        JOptionPane.showMessageDialog(null, "Wrong REST method name: " + value);
        return null;
    }
}
