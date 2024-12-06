package prob0719;

/**
 * Command based instruction - NEEDS TO BE ADJUSTED TO DIFFERENT MNEMONICS
 *
 * <p>
 * File: <code>NonUnaryInstr.java</code>
 *
 * @author Inrig
 */

public class NonUnaryInstr extends ACode {
    private final Mnemon mnemonic;
    private final AArg addr;
    private final Mnemon addrMode;
    private final Integer increment;
    private final String comment;
    private final String symbol;


    public NonUnaryInstr(String sym, Mnemon mn, AArg a, Mnemon mode, int incr, String comm) {
        mnemonic = mn;
        addr = a;
        addrMode = mode;
        increment = incr;
        comment = ";" + comm;
        symbol = sym;
    }

    public NonUnaryInstr(String sym, Mnemon mn, AArg a, Mnemon mode, int incr) {
        mnemonic = mn;
        addr = a;
        addrMode = mode;
        increment = incr;
        comment = "";
        symbol = sym;
    }

    public NonUnaryInstr(String sym, Mnemon mn, AArg a, int incr) {
        mnemonic = mn;
        addr = a;
        addrMode = Mnemon.M_DIR; //change to be direct addressing
        increment = incr;
        comment = "";
        symbol = sym;
    }

    public NonUnaryInstr(String sym, Mnemon mn, AArg a, int incr, String comm) {
        mnemonic = mn;
        addr = a;
        addrMode = Mnemon.M_DIR; //change to be direct addressing
        increment = incr;
        comment = ";" + comm;
        symbol = sym;
    }

    @Override
    public String generateListing() {
        String mn = Maps.mnemonStringTable.get(mnemonic);
        switch (mnemonic) {
            case M_BR:
            case M_BRLE:
            case M_BRLT:
            case M_BREQ:
                if (addrMode == Mnemon.M_IDX) {
                    return String.format("%s  %s%s%s%s %s,%s %s %s\n",
                            Util.toHex(increment,4),symbol,Util.spacePut(9-symbol.length()), mn,Util.spacePut(7 - mn.length()),
                            addr.generateCode(), Maps.mnemonStringTable.get(addrMode),Util.spacePut(9 - Maps.mnemonStringTable.get(addrMode).length() - addr.generateCode().length()),comment);
                } else {
                    return String.format("%s  %s%s%s%s %s %s %s\n",
                            Util.toHex(increment,4),symbol,Util.spacePut(9-symbol.length()), mn, Util.spacePut(7 - mn.length()), addr.generateCode(),Util.spacePut(10 - addr.generateCode().length()),comment);
                }
            case M_BLOCK:
                return String.format("%s  %s%s.%s%s %s             %s\n",
                        Util.toHex(increment,4), symbol,Util.spacePut(9-symbol.length()), mn,
                        Util.spacePut(12 - mn.length()),addr.generateCode(),comment);
            default: //for whatever reason takes over the dotinstr command
                if (addr != null) {
                    return String.format("%s  %s%s%s%s %s,%s %s %s\n",
                            Util.toHex(increment,4),symbol,Util.spacePut(9-symbol.length()), mn,
                            Util.spacePut(7 - mn.length()), addr.generateCode(), Maps.mnemonStringTable.get(addrMode),
                            Util.spacePut(5 - Maps.mnemonStringTable.get(addrMode).length()),comment);
                } else {
                    return String.format("%s  %s%s%s%s %s          %s\n",
                            Util.toHex(increment,4),symbol,Util.spacePut(9-symbol.length()), mn, Util.spacePut(7 - mn.length()), addr.generateCode(),comment);
                }
        }
    }

    @Override
    public String generateCode() {
        Maps.addrValTable.get(addrMode);
        switch (mnemonic) {
            case M_BR: //idea to get mnemonic as a decimal, add the addressing mode to it then convert back to hex
                //return (Util.toHex(18 + Maps.addrValTable.get(addrMode),2) + " " + Util.toBiHex(addr.getValue()) + "\n");
            case M_BRLT:
            case M_BREQ:
            case M_BRLE:
                return Util.toML(Maps.mnemonIntTable.get(mnemonic),addrMode,addr,5);
            case M_CPWA:
            case M_DECI:
            case M_DECO:
            case M_ADDA:
            case M_SUBA:
            case M_STWA:
            case M_LDWA:
                return Util.toML(Maps.mnemonIntTable.get(mnemonic),addrMode,addr,1);
            default:
                return Util.toML(Maps.mnemonIntTable.get(mnemonic),addrMode,addr,1);
        }
    }
}