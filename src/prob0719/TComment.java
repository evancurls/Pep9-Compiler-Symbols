package prob0719;

/**
 * Inheritance class of AToken, declares Token as Identifier.
 *
 * <p>
 * File: <code>TComment.java</code>
 *
 * @author Inrig
 */

public class TComment extends AToken {
    private final String stringValue;
    public TComment(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    public String getStringValue(){ return stringValue; }
    @Override
    public String getDescription() {
        return String.format("Comment         = %s", stringValue);
    }
}
