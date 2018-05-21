package rules.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Andrey Zhelezny
 *         Date: 4/26/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Destination {
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private ApplyTo[] applyTo;
    private RuleValueType type;
    private RuleRegexReplace regex;

    public ApplyTo[] getApplyTo() {
        return applyTo;
    }

    public Destination setApplyTo(ApplyTo[] applyTo) {
        this.applyTo = applyTo;
        return this;
    }

    public RuleValueType getType() {
        return type;
    }

    public Destination setType(RuleValueType type) {
        this.type = type;
        return this;
    }

    public RuleRegexReplace getRegex() {
        return regex;
    }

    public Destination setRegex(RuleRegexReplace regex) {
        this.regex = regex;
        return this;
    }
}
