package prob0719;

/**
 * A decimal argument subclass
 *
 * <p>
 * File: <code>DecArg.java</code>
 *
 * @author Inrig
 */

public class DecArg extends AArg {
    private final int decValue;
    public DecArg(int i) {
        decValue = i;
    }

    @Override
    public final int getValue(){
        return decValue;
    }
    @Override
    public String generateCode() {
        return String.format("%d",decValue);
    }
}