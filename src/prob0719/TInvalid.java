package prob0719;

/**
 * Inheritance class of AToken, provides errors in case of Invalid Token.
 *
 * <p>
 * File: <code>TInvalid.java</code>
 *
 * @author Inrig
 */


public class TInvalid extends AToken {
    private final int errCode;
    private final String errMsg;
    private final int errNum;

    public TInvalid(int code) {
        errCode = code;
        errMsg = "";
        errNum = 0;
    }
    public TInvalid(int code,String m) {
        errCode = code;
        errMsg = m;
        errNum = 0;
    }
    public TInvalid(int code,int m) {
        errCode = code;
        errMsg = "";
        errNum = m;
    }
    public TInvalid() {
        errCode = 0;
        errMsg = "";
        errNum = 0;
    }
    @Override
    public String getDescription() {
        if (errCode == 0) {
            return "Syntax error";
        } else if (errCode == 1){ //Overflow
            return "Syntax error; Overflow of following input: " + errNum;
        } else if (errCode == 2){
            return "Syntax error; Invalid Hex: " + errMsg;
        } else if (errCode == 3){
           return "Syntax error; Overflowing Hex: " + errNum;
        } else if (errCode == 4){
            return "Syntax error; Invalid Character";
        }else {
            return "Unrecognized Syntax error";
        }
    }
}