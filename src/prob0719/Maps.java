package prob0719;
import java.util.*;

/**
 * Mnemonic States.
 *
 * <p>
 * File: <code>Mnemon.java</code>
 *
 * @author Inrig
 *
*
*   M_STOP, M_ASLA, M_ASRA, //unary instructions
*   M_BR, M_BRLT, M_BREQ, M_BRLE, M_CPWA, M_DECI, //nonunary instructions
*   M_DECO, M_ADDA, M_SUBA, M_STWA, M_LDWA, //nonunary instructions
*   M_BLOCK, M_END //dot commands

*/

public final class Maps {
    public static final Map<String, Mnemon> unaryMnemonTable;
    public static final Map<String, Mnemon> nonUnaryMnemonTable;
    public static final Map<String, Mnemon> dotMnemonTable;

    public static final Map<String,Mnemon> addrMnemonTable;
    public static final Map<Mnemon,Integer> addrValTable;


    public static final Map<Mnemon, String> mnemonStringTable;

    public static final Map<Mnemon, Integer> mnemonIntTable;

    static {
        unaryMnemonTable = new HashMap<>();
        unaryMnemonTable.put("stop", Mnemon.M_STOP);
        unaryMnemonTable.put("asla", Mnemon.M_ASLA);
        unaryMnemonTable.put("asra", Mnemon.M_ASRA);

        nonUnaryMnemonTable = new HashMap<>();
        nonUnaryMnemonTable.put("br", Mnemon.M_BR);
        nonUnaryMnemonTable.put("brlt", Mnemon.M_BRLT);
        nonUnaryMnemonTable.put("breq", Mnemon.M_BREQ);
        nonUnaryMnemonTable.put("brle", Mnemon.M_BRLE);
        nonUnaryMnemonTable.put("cpwa", Mnemon.M_CPWA);
        nonUnaryMnemonTable.put("cpba", Mnemon.M_CPBA);
        nonUnaryMnemonTable.put("deci", Mnemon.M_DECI);
        nonUnaryMnemonTable.put("deco", Mnemon.M_DECO);
        nonUnaryMnemonTable.put("adda", Mnemon.M_ADDA);
        nonUnaryMnemonTable.put("suba", Mnemon.M_SUBA);
        nonUnaryMnemonTable.put("stwa", Mnemon.M_STWA);
        nonUnaryMnemonTable.put("stba", Mnemon.M_STBA);
        nonUnaryMnemonTable.put("ldwa", Mnemon.M_LDWA);
        nonUnaryMnemonTable.put("ldba", Mnemon.M_LDBA);

        dotMnemonTable = new HashMap<>();
        dotMnemonTable.put("block", Mnemon.M_BLOCK);
        dotMnemonTable.put("end", Mnemon.M_END);


        //M_IMM, M_DIR, M_IND, M_STR, M_STRD, M_IDX, M_STIDX, M_STDIDX //addressing modes

        addrMnemonTable = new HashMap<>(); //in case i need mnemonics attached
        addrMnemonTable.put("i", Mnemon.M_IMM);
        addrMnemonTable.put("d", Mnemon.M_DIR);
        addrMnemonTable.put("n", Mnemon.M_IND);
        addrMnemonTable.put("s", Mnemon.M_STR);
        addrMnemonTable.put("sf", Mnemon.M_STRD);
        addrMnemonTable.put("x", Mnemon.M_IDX);
        addrMnemonTable.put("sx", Mnemon.M_STIDX);
        addrMnemonTable.put("sfx", Mnemon.M_STDIDX);

        addrValTable = new HashMap<>(); //integer value attached to each addressing mode
        addrValTable.put(Mnemon.M_IMM, 0);
        addrValTable.put(Mnemon.M_DIR, 1);
        addrValTable.put(Mnemon.M_IND, 2);
        addrValTable.put(Mnemon.M_STR, 3);
        addrValTable.put(Mnemon.M_STRD, 4);
        addrValTable.put(Mnemon.M_IDX, 5);
        addrValTable.put(Mnemon.M_STIDX, 6);
        addrValTable.put(Mnemon.M_STDIDX, 7);

        mnemonIntTable = new EnumMap<>(Mnemon.class); //integer value attached to each addressing mode
        mnemonIntTable.put(Mnemon.M_STOP, 0);
        mnemonIntTable.put(Mnemon.M_ASLA, 10);
        mnemonIntTable.put(Mnemon.M_ASRA, 12);

        mnemonIntTable.put(Mnemon.M_BR, 18);
        mnemonIntTable.put(Mnemon.M_BRLT, 22);
        mnemonIntTable.put(Mnemon.M_BREQ, 24);
        mnemonIntTable.put(Mnemon.M_BRLE, 20);

        mnemonIntTable.put(Mnemon.M_CPWA, 160);
        mnemonIntTable.put(Mnemon.M_CPBA, 176);


        mnemonIntTable.put(Mnemon.M_DECI, 48);
        mnemonIntTable.put(Mnemon.M_DECO, 56);
        mnemonIntTable.put(Mnemon.M_ADDA, 96);
        mnemonIntTable.put(Mnemon.M_SUBA, 112);
        mnemonIntTable.put(Mnemon.M_STWA, 224);
        mnemonIntTable.put(Mnemon.M_STBA, 240);

        mnemonIntTable.put(Mnemon.M_LDWA, 192);
        mnemonIntTable.put(Mnemon.M_LDBA, 208);


        mnemonStringTable = new EnumMap<>(Mnemon.class);
        mnemonStringTable.put(Mnemon.M_STOP, "STOP");
        mnemonStringTable.put(Mnemon.M_ASLA, "ASLA");
        mnemonStringTable.put(Mnemon.M_ASRA, "ASRA");

        mnemonStringTable.put(Mnemon.M_BR, "BR  ");
        mnemonStringTable.put(Mnemon.M_BRLT, "BRLT");
        mnemonStringTable.put(Mnemon.M_BREQ, "BREQ");
        mnemonStringTable.put(Mnemon.M_BRLE, "BRLE");
        mnemonStringTable.put(Mnemon.M_CPWA, "CPWA");
        mnemonStringTable.put(Mnemon.M_CPBA, "CPBA");
        mnemonStringTable.put(Mnemon.M_DECI, "DECI");
        mnemonStringTable.put(Mnemon.M_DECO, "DECO");
        mnemonStringTable.put(Mnemon.M_ADDA, "ADDA");
        mnemonStringTable.put(Mnemon.M_SUBA, "SUBA");
        mnemonStringTable.put(Mnemon.M_STWA, "STWA");
        mnemonStringTable.put(Mnemon.M_STBA, "STBA");
        mnemonStringTable.put(Mnemon.M_LDWA, "LDWA");
        mnemonStringTable.put(Mnemon.M_LDBA, "LDBA");

        mnemonStringTable.put(Mnemon.M_BLOCK, "BLOCK");
        mnemonStringTable.put(Mnemon.M_END, "END");

        mnemonStringTable.put(Mnemon.M_IMM, "i");
        mnemonStringTable.put(Mnemon.M_DIR, "d");
        mnemonStringTable.put(Mnemon.M_IND, "n");
        mnemonStringTable.put(Mnemon.M_STR, "s");
        mnemonStringTable.put(Mnemon.M_STRD, "sf");
        mnemonStringTable.put(Mnemon.M_IDX, "x");
        mnemonStringTable.put(Mnemon.M_STIDX, "sx");
        mnemonStringTable.put(Mnemon.M_STDIDX, "sfx");


    }
}

