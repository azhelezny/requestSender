package rules.classes;

/**
 * @author Andrey Zhelezny
 *         Date: 4/26/18
 */
public enum RuleValueType {
    VALUE("VALUE"),
    REGEX("REGEX");

    private String value;

    RuleValueType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
