package prob0719;

/**
 * Command based instruction - NEEDS TO BE ADJUSTED TO DIFFERENT MNEMONICS
 *
 * <p>
 * File: <code>UnaryInstr.java</code>
 *
 * @author Inrig
 */

public class UnaryInstr extends ACode {
    private final Mnemon mnemonic;
    private final Integer increment;
    private final String comment;
    private final String symbol;

    public UnaryInstr(String sym, Mnemon mn, int incr, String comm) {
        mnemonic = mn;
        increment = incr;
        comment = ";" + comm;
        symbol = sym;
    }

    public UnaryInstr(String sym, Mnemon mn, int incr) {
        mnemonic = mn;
        increment = incr;
        comment = "";
        symbol = sym;
    }
    @Override
    public String generateListing() {
        return String.format("%s  %s %s %s%s%s\n",
                Util.toHex(increment,4),symbol,Util.spacePut(7 - symbol.length()), Maps.mnemonStringTable.get(mnemonic),Util.spacePut(20 - Maps.mnemonStringTable.get(mnemonic).length()),comment);
    }
    @Override
    public String generateCode() {
        switch (mnemonic) {
            case M_STOP:
                return "00\n";
            case M_ASLA:
                return "0A\n";
            case M_ASRA:
                return "0C\n";
            default:
                return "\n"; //in case of nothing, unlikely to occur
        }
    }
}
