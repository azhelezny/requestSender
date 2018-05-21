package rules.classes;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Andrey Zhelezny
 *         Date: 4/26/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {
    private CollectFrom[] collectFrom;
    private RuleValueType type;
    private String value;
    private RuleRegexFind regex;
    private boolean defined = false;

    public CollectFrom[] getCollectFrom() {
        return collectFrom;
    }

    public boolean isDefined() {
        return this.defined;
    }

    public Source setCollectFrom(CollectFrom[] collectFrom) {
        this.collectFrom = collectFrom;
        return this;
    }

    public RuleValueType getType() {
        return type;
    }

    public Source setType(RuleValueType type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Source setValue(String value) {
        this.value = value;
        this.defined = true;
        return this;
    }

    public RuleRegexFind getRegex() {
        return regex;
    }

    public Source setRegex(RuleRegexFind regex) {
        this.regex = regex;
        return this;
    }
}
