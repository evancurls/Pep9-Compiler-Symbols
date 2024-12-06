package prob0719;
import java.util.*;


public final class Symbols {
    //public Map<String, String> SymbolTable;

    //Symbols.SymbolTable = new HashMap<>();
    public static HashMap<String, Integer> symbolTable = new HashMap<>();

    public void putSymbol(String str, Integer addr){
        symbolTable.put(str, addr);
    }

}
