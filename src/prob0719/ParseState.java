package prob0719;

/**
 * The enumerated values for the parsed states of the finite state machine.
 *
 * <p>
 * File: <code>ParseState.java</code>
 *
 * @author Inrig
 */

public enum ParseState {
    PS_START, PS_UNARY, PS_NONUNARY, PS_CONST, PS_ADDR, PS_DOT, PS_DOTCONST, PS_NEWSYMBOL, PS_COMMENT, PS_SYMBOL, PS_FINAL
}
