package prob0719;

/**
 * Inheritance class of AToken, declares Token as Identifier.
 *
 * <p>
 * File: <code>TSymbol.java</code>
 *
 * @author Inrig
 */

public class TSymbol extends AToken {
    private final String stringValue;
    public TSymbol(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    public String getStringValue(){ return stringValue; }
    @Override
    public String getDescription() {
        return String.format("Symbol        = %s", stringValue);
    }
}