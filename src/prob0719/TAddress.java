package prob0719;

/**
 * Inheritance class of AToken, declares Token as an Address.
 *
 * <p>
 * File: <code>TAddress.java</code>
 *
 * @author Inrig
 */

public class TAddress extends AToken {
    private final String stringValue;
    public TAddress(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    public String getStringValue(){ return stringValue; }
    @Override
    public String getDescription() {
        return String.format("Addressing Mode = %s", stringValue);
    }
}
