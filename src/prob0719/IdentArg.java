package prob0719;

/**
 * Identity Argument
 *
 * <p>
 * File: <code>IdentArg.java</code>
 *
 * @author Inrig
 */

public class IdentArg extends AArg {
    private final String identValue;
    public IdentArg(String str) {
        identValue = str;
    }
    public IdentArg() {
        identValue = "";
    }


    @Override
    public final int getValue(){
        return 1;
    }
    @Override
    public String generateCode() {
        return identValue;
    }
}
