package prob0719;

/**
 * Inheritance class of AToken, declares Token as Identifier.
 *
 * <p>
 * File: <code>TDot.java</code>
 *
 * @author Inrig
 */

public class TDot extends AToken {
    private final String stringValue;
    public TDot(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    public String getStringValue(){ return stringValue; }
    @Override
    public String getDescription() {
        return String.format("Dot command     = %s", stringValue);
    }
}
