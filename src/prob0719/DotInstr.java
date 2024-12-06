package prob0719;

/**
 * Dot based instruction - NEEDS TO BE ADJUSTED TO DIFFERENT MNEMONICS
 *
 * <p>
 * File: <code>DotInstr.java</code>
 *
 * @author Inrig
 */

public class DotInstr extends ACode {
    private final Mnemon mnemonic;
    private final AArg addr;
    private final Integer increment;
    private final String comment;
    private final String symbol;

    public DotInstr(String sym, Mnemon mn, int incr) { //no addressing mode
        mnemonic = mn;
        addr = new DecArg(0); //set to nothing as default
        increment = incr;
        comment = "";
        symbol = sym;
    }

    public DotInstr(String sym, Mnemon mn, int incr, String comm) { //no addressing mode
        mnemonic = mn;
        addr = new DecArg(0); //set to nothing as default
        increment = incr;
        comment = ";" + comm;
        symbol = sym;
    }

    public DotInstr(String sym, Mnemon mn, AArg address, int incr) {
        mnemonic = mn;
        addr = address; //set to number of blocked spaces
        increment = incr;
        comment = "";
        symbol = sym;
    }

    public DotInstr(String sym, Mnemon mn, AArg address, int incr, String comm) {
        mnemonic = mn;
        addr = address; //set to number of blocked spaces
        increment = incr;
        comment = ";" + comm;
        symbol = sym;
    }

    @Override
    public String generateListing() {
        String mn = Maps.mnemonStringTable.get(mnemonic);
        switch (mnemonic) {
            case M_BLOCK:
                return String.format("%s  %s %s .%s  %s %s %s\n",
                        Util.toHex(increment,4),symbol, Util.spacePut(7 - symbol.length()), mn,
                        addr.generateCode(),Util.spacePut(10 - addr.generateCode().length()),comment);
            case M_END:
                return String.format("%s  %s %s .%s %s %s\n",
                        Util.toHex(increment,4),symbol,Util.spacePut(7 - symbol.length()),mn,Util.spacePut(14),comment);
            default:
                return String.format("%s %s .%s     %s %s %s\n",
                        Util.toHex(increment,4),Util.spacePut(7), mn,addr.generateCode(),Util.spacePut(11 - addr.generateCode().length()),comment); //in case of nothing, unlikely to occur
        }
    }


    @Override
    public String generateCode() {
        switch (mnemonic) {
            case M_BLOCK:
                return ("00 ".repeat(addr.getValue()) + "\n"); //needs to be 00 x num of/at address
            case M_END:
                return "zz";
            default:
                return ""; //in case of nothing, unlikely to occur
        }
    }
}