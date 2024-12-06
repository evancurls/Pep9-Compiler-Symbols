package prob0719;

/**
 * Inheritance class of AToken, declares Token as Integer.
 *
 * <p>
 * File: <code>TInteger.java</code>
 *
 * @author Inrig
 */

public class TInteger extends AToken {
    private final int intValue;
    public TInteger(int i) {
        intValue = i;
    }

    public int getIntValue(){ return intValue; }

    @Override
    public String getDescription() {
        return String.format("Integer         = %d", intValue);
    }
}