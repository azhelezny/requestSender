package rules.classes;

/**
 * @author Andrey Zhelezny
 *         Date: 4/26/18
 */
public class RuleRegexReplace {
    private String searchPattern;
    private String replacePattern;

    public String getSearchPattern() {
        return searchPattern;
    }

    public RuleRegexReplace setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
        return this;
    }

    public String getReplacePattern() {
        return replacePattern;
    }

    public RuleRegexReplace setReplacePattern(String replacePattern) {
        this.replacePattern = replacePattern;
        return this;
    }
}
