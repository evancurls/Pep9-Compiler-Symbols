package prob0719;

/**
 * Utilities for parsing, ex: alphanumeric and integer checks.
 *
 * <p>
 * File: <code>Util.java</code>
 *
 * @author Inrig
 */

public class Util {
    public static boolean isDigit(char ch) {
        return ('0' <= ch) && (ch <= '9');
    }
    public static boolean isAlpha(char ch) {
        return (('a' <= ch) && (ch <= 'z') || ('A' <= ch) && (ch <= 'Z'));
    }
    public static int changeHex(char ch) {
        if (('0' <= ch) && (ch <= '9')){
            return ch - '0';
        } else if (('a' <= ch) && (ch <= 'z')){
            return ch - 'W';
        } else {
            return ch - '7';
        }
    }
    public static boolean isHex(char ch) {
        return ('0' <= ch) && (ch <= '9') || ('a' <= ch) && (ch <= 'f') || ('A' <= ch) && (ch <= 'F');
    }

//    public static String convertHex(int i){
//        final String stringValue;
//        Integer tempInt;
//        StringBuffer tempString = new StringBuffer();
//        for (int j = 0; j < 4; j++){
//            tempString.append();
//        }
//        stringValue = new String(tempString);
//        return stringValue;
//    }

    public static String toHex(int i, int len){ //assuming it is between
        StringBuffer tempstr = new StringBuffer();
        String str;
        int temp;
        char[] HexValues;
        HexValues = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        for(int j = 0; j < len; j++){
            temp = i%16;
            tempstr.insert(0,HexValues[temp]);
            i = i/16;
        }
        str = new String(tempstr);
        return str;
    }

    public static String toBiHex(int i) {
        StringBuffer tempstr = new StringBuffer();
        StringBuffer tempstr2 = new StringBuffer();
        String str;
        int temp;
        if (i < 0){
            i += 65536;
        }
        char[] HexValues;
        HexValues = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        for(int j = 0; j < 2; j++){
            temp = i%16;
            tempstr.insert(0,HexValues[temp]);
            i = i/16;
        }
        for(int k = 0; k < 2; k++){
            temp = i%16;
            tempstr2.insert(0,HexValues[temp]);
            i = i/16;
        }
        str = new String(tempstr2 + " " + tempstr);
       // System.out.println("toBiHex String: " + str + "\n");
        return str;
    }

    public static String toML(int i, Mnemon addrMode, AArg addr,int thing){
        return (Util.toHex(i + (Maps.addrValTable.get(addrMode) / thing),2) +
                " " + Util.toBiHex(addr.getValue()) + "\n");
    }

    public static String spacePut(int i){
        return String.format("%"+i+"s", "");
    }
}