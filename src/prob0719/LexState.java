package prob0719;

/**
 * The enumerated values for the states of the finite state machine.
 *
 * <p>
 * File: <code>LexState.java</code>
 *
 * @author Inrig
 */

public enum LexState {
    LS_START, LS_INT1, LS_INT2, LS_HEX1, LS_HEX2, LS_IDENT, LS_SIGN, LS_DOT1, LS_DOT2, LS_ADDR1, LS_ADDR2,LS_COMMENT, LS_STOP
}
