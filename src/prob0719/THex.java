package prob0719;

/**
 * Inheritance class of AToken, declares Token as Hexadecimal.
 *
 * <p>
 * File: <code>TInteger.java</code>
 *
 * @author Inrig
 */

public class THex extends AToken {
    private final int hexValue;
    public THex(int i) {
        hexValue = i;
    }
    public int getHexValue(){ return hexValue; }
    @Override
    public String getDescription() {
        return String.format("Hex Constant    = %d", hexValue);
    }
}