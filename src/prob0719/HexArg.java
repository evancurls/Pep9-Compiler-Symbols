package prob0719;

/**
 * A hex argument subclass
 *
 * <p>
 * File: <code>DecArg.java</code>
 *
 * @author Inrig
 */

public class HexArg extends AArg {
    private final int identValue;
    public HexArg(int i) {
        identValue = i;
    }
    @Override
    public final int getValue(){
        return identValue;
    }
    @Override
    public String generateCode() {
        String tempStr = Util.toHex(identValue,4);
        return String.format("0x%s",tempStr);
    }
}