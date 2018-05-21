package rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import rules.classes.ApplyTo;
import rules.classes.CollectFrom;
import rules.classes.Destination;
import rules.classes.Source;

/**
 * @author Andrey Zhelezny
 *         Date: 4/11/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule {
    private String caption;
    private Source source;
    private Destination [] destination;

    public String getCaption() {
        return caption;
    }

    public Rule setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Source getSource() {
        return source;
    }

    public Rule setSource(Source source) {
        this.source = source;
        return this;
    }

    public Destination[] getDestination() {
        return destination;
    }

    public Rule setDestination(Destination[] destination) {
        this.destination = destination;
        return this;
    }
}
