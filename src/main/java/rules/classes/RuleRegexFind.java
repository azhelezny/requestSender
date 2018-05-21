package rules.classes;

/**
 * @author Andrey Zhelezny
 *         Date: 4/26/18
 */
public class RuleRegexFind {
    private String value;
    private int group;

    public String getValue() {
        return value;
    }

    public RuleRegexFind setValue(String value) {
        this.value = value;
        return this;
    }

    public int getGroup() {
        return group;
    }

    public RuleRegexFind setGroup(int group) {
        this.group = group;
        return this;
    }
}
