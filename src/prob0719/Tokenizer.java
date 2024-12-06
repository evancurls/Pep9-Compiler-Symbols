package prob0719;

/**
 * Where everything happens.
 *
 * <p>
 * File: <code>Util.java</code>
 *
 * @author Inrig
 */

public class Tokenizer {
    private final InBuffer b;
    public Tokenizer(InBuffer inBuffer) {
        b = inBuffer;
    }
    public AToken getToken() {
        char nextChar;
        StringBuffer localStringValue = new StringBuffer("");
        int localIntValue = 0;
        int sign = 1;
        AToken aToken = new TEmpty();
        LexState state = LexState.LS_START;
        do {
            nextChar = b.advanceInput();
            switch (state) {
                case LS_START:
                    if (Util.isAlpha(nextChar)) { //Goes to Identity
                        localStringValue.append(nextChar);
                        state = LexState.LS_IDENT;
                    } else if (nextChar == '-') { //Goes to Sign
                        sign = -1;
                        state = LexState.LS_SIGN;
                    } else if (nextChar == '+') { //Goes to Sign
                        sign = 1;
                        state = LexState.LS_SIGN;
                    } else if (Util.isDigit(nextChar)) { //Goes to Int
                        localIntValue = nextChar - '0';
                        if (nextChar == '0'){ //Int 1
                            state = LexState.LS_INT1;
                        } else { //Int 2
                            state = LexState.LS_INT2;
                        }
                    } else if (nextChar == '\n') { //Stop State
                        state = LexState.LS_STOP;
                    } else if (nextChar == '.') { //Goes to Dot
                        state = LexState.LS_DOT1;
                    } else if (nextChar == ',') { //Goes to Address
                        state = LexState.LS_ADDR1;
                    } else if (nextChar == ';') { //Goes to Comment
                        state = LexState.LS_COMMENT;
                    }else if (nextChar != ' ') { //Invalid
                        aToken = new TInvalid(4);
                    }
                    break;
                case LS_INT1: //goes to int2 or hex1
                    if (nextChar == 'x' || nextChar == 'X') { //to complete
                        state = LexState.LS_HEX1;
                    } else {
                        b.backUpInput();
                        state = LexState.LS_INT2;
                    }
                    break;
                case LS_INT2: //goes to self only
                    if (Util.isDigit(nextChar)) {
                        localIntValue = 10 * localIntValue + nextChar - '0'; //clean up text below
                        if ((sign == 1 && localIntValue > 65535) || (sign == -1 && localIntValue > 32768)) {
                            aToken = new TInvalid(1, (sign * localIntValue));
                        }
                    } else {
                        b.backUpInput();
                        aToken = new TInteger(sign * localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_HEX1: //goes to hex2 only
                    if (Util.isHex(nextChar)) {
                        b.backUpInput();
                        state = LexState.LS_HEX2;
                    } else {
                        aToken = new TInvalid(2, nextChar); //if input is not a hex
                    }
                    break;
                case LS_HEX2: // goes to self only
                    if (Util.isHex(nextChar)) {
                        localIntValue = 16 * localIntValue + Util.changeHex(nextChar);
                        if (localIntValue > 65535){
                            aToken = new TInvalid(3, localIntValue);
                        }
                    } else {
                        b.backUpInput();
                        aToken = new THex(localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_IDENT: //self only with letter or digit
                    if (Util.isAlpha(nextChar) || Util.isDigit(nextChar)) {
                        localStringValue.append(nextChar);
                    } else if (nextChar == ':' && localStringValue.length() < 9) {
                        aToken = new TSymbol(localStringValue);
                        state = LexState.LS_STOP;
                    } else {
                        b.backUpInput();
                        aToken = new TIdentifier(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_SIGN:
                    if (Util.isDigit(nextChar)) {
                        localIntValue = nextChar - '0';
                        state = LexState.LS_INT2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_DOT1:
                    if(Util.isAlpha(nextChar)) {
                        b.backUpInput();
                        state = LexState.LS_DOT2;
                    } else {
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_DOT2:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TDot(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_ADDR1:
                    if(nextChar == ' '){
                        break;
                    } else if(Util.isAlpha(nextChar)) {
                        b.backUpInput();
                        state = LexState.LS_ADDR2;
                    } else {
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_ADDR2:
                    if(Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TAddress(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_COMMENT:
                    if(nextChar != '\n') {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TComment(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
            }
        } while ((state != LexState.LS_STOP) && !(aToken instanceof TInvalid));
        return aToken;
    }
}