package prob0719;

/**
 * Mnemonic States.
 *
 * <p>
 * File: <code>Mnemon.java</code>
 *
 * @author Inrig
 */

public enum Mnemon {
    M_STOP, M_ASLA, M_ASRA, //unary instructions
    M_BR, M_BRLT, M_BREQ, M_BRLE, M_CPWA, M_CPBA,M_DECI, //nonunary instructions
    M_DECO, M_ADDA, M_SUBA, M_STWA, M_STBA, M_LDWA, M_LDBA,//nonunary instructions
    M_BLOCK, M_END, //dot commands
    M_IMM, M_DIR, M_IND, M_STR, M_STRD, M_IDX, M_STIDX, M_STDIDX //addressing modes
}
