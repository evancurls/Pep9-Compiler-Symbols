package prob0719;

/**
 * A symbol argument subclass
 *
 * <p>
 * File: <code>DecArg.java</code>
 *
 * @author Inrig
 */

public class SymArg extends AArg {
    private final String SymValue;
    public SymArg(String i) {
        SymValue = i;
    }

    @Override
    public final int getValue(){
        return SymbolMaps.symbolValueTable.get(SymValue);
    }
    @Override
    public String generateCode() {
        return SymValue;
    }
}