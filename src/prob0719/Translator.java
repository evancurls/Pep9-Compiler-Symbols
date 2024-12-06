package prob0719;

import java.util.*;

/**
 * The Big Guy
 *
 * <p>
 * File: <code>Translator.java</code>
 *
 * @author Inrig
 */

public class Translator {
    private final InBuffer b;
    private Tokenizer t;
    private ACode aCode;
    private Integer increment = 0;
    private Integer symbolincrement = 0;
    private final InBuffer symBuffer;
    private Tokenizer symToken;
    private ACode symCode;

    public Translator(InBuffer inBuffer, InBuffer inBuffer2) {
        b = inBuffer;
        symBuffer = inBuffer2;
    }
    //similar to parseLine() except will run before it and process each symbol into a symbol table
    private void symbolParse(){
        AArg address = null;
        String symbol = "";
        Mnemon addrMode = Mnemon.M_DIR;
        Mnemon localMnemon = Mnemon.M_END;
        AToken aToken;
        aCode = new EmptyInstr();
        boolean leave = false;
        ParseState state = ParseState.PS_START;
        do {
            aToken = symToken.getToken();
            switch (state) {
                case PS_START:
                    if (aToken instanceof TIdentifier) { //determines unary or nonunary
                        TIdentifier localTIdentifier = (TIdentifier) aToken;
                        String tempStr = localTIdentifier.getStringValue();

                        if (Maps.unaryMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.unaryMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_UNARY;
                        } else if (Maps.nonUnaryMnemonTable.containsKey(
                                tempStr.toLowerCase())) {
                            localMnemon = Maps.nonUnaryMnemonTable.get(
                                    tempStr.toLowerCase());
                            //aCode = new NonUnaryInstr(localMnemon);
                            state = ParseState.PS_NONUNARY;
                        } else {
                            leave = true;
                        }
                    } else if (aToken instanceof TDot) { //add DotInstr
                        TDot localTDot = (TDot) aToken;
                        String tempStr = localTDot.getStringValue();
                        if (Maps.dotMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.dotMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_DOT;
                        } else {
                            leave = true;
                        }
                    } else if (aToken instanceof TSymbol){
                        //MAKING NEW SYMBOL HERE!!!
                        TSymbol localTSymbol = (TSymbol) aToken;
                        SymbolMaps.symbolValueTable.put(localTSymbol.getStringValue(), symbolincrement);
                        //System.out.printf("Symbol: %s\nSymbol Value: %s\n",localTSymbol.getStringValue(),SymbolMaps.symbolValueTable.get(localTSymbol.getStringValue()));
                        state = ParseState.PS_NEWSYMBOL;
                    } else if (aToken instanceof TComment){
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        state = ParseState.PS_FINAL;
                    } else if (aToken instanceof TEmpty) {
                        state = ParseState.PS_FINAL;
                    } else{
                        leave = true;
                    }
                    break;
                case PS_NEWSYMBOL: //not sure if i need this yet, could likely be implemented in start
                    if (aToken instanceof TIdentifier) { //determines unary or nonunary
                        TIdentifier localTIdentifier = (TIdentifier) aToken;
                        String tempStr = localTIdentifier.getStringValue();
                        if (Maps.unaryMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.unaryMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_UNARY;
                        } else if (Maps.nonUnaryMnemonTable.containsKey(
                                tempStr.toLowerCase())) {
                            localMnemon = Maps.nonUnaryMnemonTable.get(
                                    tempStr.toLowerCase());
                            //aCode = new NonUnaryInstr(localMnemon);
                            state = ParseState.PS_NONUNARY;
                        } else{
                            leave = true;
                        }
                    } else if (aToken instanceof TDot) { //add DotInstr
                        TDot localTDot = (TDot) aToken;
                        String tempStr = localTDot.getStringValue();
                        if (Maps.dotMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.dotMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_DOT;
                        } else{
                            leave = true;
                        }
                    }
                    break;
                case PS_UNARY:
                    if (aToken instanceof TEmpty) {
                        state = ParseState.PS_FINAL;
                        symbolincrement++;
                    } else if (aToken instanceof TComment){
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        state = ParseState.PS_FINAL;
                        symbolincrement++;
                    } else{
                        leave = true;
                    }
                    break;
                case PS_NONUNARY:
                    if (aToken instanceof TInteger) {
                        TInteger localTInteger = (TInteger) aToken;
                        Integer tempInt = localTInteger.getIntValue();
                        address = new DecArg(tempInt); //sets address to a new decimal
                        state = ParseState.PS_CONST;
                    } else if (aToken instanceof THex) {
                        THex localTHex = (THex) aToken;
                        Integer tempInt = localTHex.getHexValue();
                        address = new HexArg(tempInt); //sets address to a new hex

                        state = ParseState.PS_CONST;
                    } else if (aToken instanceof TIdentifier) {
                        TIdentifier localTString = (TIdentifier) aToken;
                        String tempStr = localTString.getStringValue();
                        address = new DecArg(0);
                        state = ParseState.PS_CONST;
                    } else{
                        leave = true;
                    }
                    break;
                case PS_DOT: //either .block or .end
                    if (aToken instanceof TEmpty) {
                        if(localMnemon != Mnemon.M_BLOCK){
                            symbolincrement++;
                            state = ParseState.PS_FINAL;
                        } else{
                            leave = true;
                        }
                    } else if (aToken instanceof TComment){
                        TComment localTComment = (TComment) aToken;
                        if (localMnemon != Mnemon.M_BLOCK){
                            String tempStr = localTComment.getStringValue();
                            symbolincrement++;
                            state = ParseState.PS_FINAL;
                        } else{
                            leave = true;
                        }
                    }else if (aToken instanceof THex && localMnemon == Mnemon.M_BLOCK) {
                        THex localTHex = (THex) aToken;
                        Integer tempInt = localTHex.getHexValue();
                        address = new HexArg(tempInt);
                        state = ParseState.PS_DOTCONST;
                    } else if (aToken instanceof TInteger && localMnemon == Mnemon.M_BLOCK) {
                        TInteger localTInteger = (TInteger) aToken;
                        Integer tempInt = localTInteger.getIntValue();
                        if (tempInt < 0){
                            leave = true;
                        } else {
                            address = new DecArg(tempInt);
                            state = ParseState.PS_DOTCONST;
                        }
                    } else if (aToken instanceof TIdentifier && localMnemon == Mnemon.M_BLOCK) {
                        TIdentifier localTString = (TIdentifier) aToken;
                        String tempStr = localTString.getStringValue();
                        address = new DecArg(0);
                        state = ParseState.PS_DOTCONST;
                    } else{
                        leave = true;
                    }
                    break;
                case PS_CONST:
                    if(aToken instanceof TAddress){ //if token gets memory address
                        TAddress localTAddress = (TAddress) aToken;
                        String tempStr = localTAddress.getStringValue();
                        if (Maps.addrMnemonTable.containsKey(tempStr.toLowerCase())) {
                            addrMode = Maps.addrMnemonTable.get(tempStr.toLowerCase());
                            //filters out mnemonics that cannot use immediate addressing: DECI & STWA
                            if(addrMode == Mnemon.M_IMM && (localMnemon == Mnemon.M_DECI || localMnemon == Mnemon.M_STWA)){
                                leave = true;
                            } else {
                                state = ParseState.PS_ADDR;
                            }
                        } else{
                            leave = true;
                        }
                    } else if (aToken instanceof TComment) {
                        if(localMnemon == Mnemon.M_BR || localMnemon == Mnemon.M_BRLT || localMnemon == Mnemon.M_BREQ || localMnemon == Mnemon.M_BRLE) {
                            TComment localTComment = (TComment) aToken;
                            String tempStr = localTComment.getStringValue();
                            state = ParseState.PS_FINAL;
                            symbolincrement = symbolincrement + 3;
                        } else {
                            leave = true;
                        }
                    } else if (aToken instanceof TEmpty) {
                        if(localMnemon == Mnemon.M_BR || localMnemon == Mnemon.M_BRLT || localMnemon == Mnemon.M_BREQ || localMnemon == Mnemon.M_BRLE) {
                            state = ParseState.PS_FINAL;
                            symbolincrement = symbolincrement + 3;
                        } else {
                            leave = true;
                        }
                    }
                    break;
                case PS_DOTCONST: //only affects .block
                    if (aToken instanceof TEmpty){
                        state = ParseState.PS_FINAL;
                        symbolincrement = symbolincrement + (address.getValue());
                    } else if (aToken instanceof TComment) {
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        state = ParseState.PS_FINAL;
                        symbolincrement = symbolincrement + (address.getValue());
                    } else if (aToken instanceof TAddress){
                        TAddress localTAddress = (TAddress) aToken;
                        String tempStr = localTAddress.getStringValue();
                        if (Maps.addrMnemonTable.containsKey(tempStr.toLowerCase())){
                            if(Maps.addrMnemonTable.get(tempStr.toLowerCase()) == Mnemon.M_DIR){
                                state = ParseState.PS_FINAL;
                                symbolincrement = symbolincrement + (address.getValue());
                            } else {
                                leave = true;
                            }
                        } else {
                            leave = true;
                        }
                    } else {
                        leave = true;
                    }
                    break;
                case PS_ADDR:
                    if (aToken instanceof TComment) {
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        state = ParseState.PS_FINAL;
                        symbolincrement = symbolincrement + 3;
                    } else if (aToken instanceof TEmpty) {
                        state = ParseState.PS_FINAL;
                        symbolincrement = symbolincrement + 3;
                    } else {
                        leave = true;
                    }
                    break;
                case PS_SYMBOL:
                    break;
                /**case PS_NEWSYMBOL: //adds new symbol to list of symbol tables, along with respective symbol index
                 TSymbol localTSymbol = (TSymbol) aToken;
                 String localStr = localTSymbol.getStringValue();
                 //Symbols.symbolTable.put(localStr, increment);
                 state = ParseState.PS_START;
                 break;
                 */
            }
        } while (state != ParseState.PS_FINAL && !leave);
    }
    // Sets aCode and returns boolean true if end statement is processed.
    private boolean parseLine() {
        boolean terminate = false;
        AArg address = null;
        String symbol = "";
        Mnemon addrMode = Mnemon.M_DIR;
        Mnemon localMnemon = Mnemon.M_END;
        AToken aToken;
        aCode = new EmptyInstr();
        ParseState state = ParseState.PS_START;
        do {
            aToken = t.getToken();
            switch (state) {
                case PS_START:
                    if (aToken instanceof TIdentifier) { //determines unary or nonunary
                        TIdentifier localTIdentifier = (TIdentifier) aToken;
                        String tempStr = localTIdentifier.getStringValue();

                        if (Maps.unaryMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.unaryMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_UNARY;
                        } else if (Maps.nonUnaryMnemonTable.containsKey(
                                tempStr.toLowerCase())) {
                            localMnemon = Maps.nonUnaryMnemonTable.get(
                                    tempStr.toLowerCase());
                            //aCode = new NonUnaryInstr(localMnemon);
                            state = ParseState.PS_NONUNARY;
                        } else { //change identifier error message
                            aCode = new Error("Illegal mnemonic");
                        }
                    } else if (aToken instanceof TDot) { //add DotInstr
                        TDot localTDot = (TDot) aToken;
                        String tempStr = localTDot.getStringValue();
                        if (Maps.dotMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.dotMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_DOT;
                        } else {
                            aCode = new Error("Illegal dot command");
                        }
                    } else if (aToken instanceof TSymbol){
                        TSymbol localTSymbol = (TSymbol) aToken;
                        //symbol should already be initialized in the table with prior pass through
                        symbol = localTSymbol.getStringValue() + ":";
                        state = ParseState.PS_NEWSYMBOL;
                    } else if (aToken instanceof TComment){
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        aCode = new ComInstr(tempStr);
                        state = ParseState.PS_FINAL;
                    } else if (aToken instanceof TEmpty) {
                        aCode = new EmptyInstr();
                        state = ParseState.PS_FINAL;
                    } else { //change default error message
                        aCode = new Error("Line must begin with function identifier.");
                    }
                    break;
                case PS_NEWSYMBOL: //not sure if i need this yet, could likely be implemented in start
                    if (aToken instanceof TIdentifier) { //determines unary or nonunary
                        TIdentifier localTIdentifier = (TIdentifier) aToken;
                        String tempStr = localTIdentifier.getStringValue();
                        if (Maps.unaryMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.unaryMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_UNARY;
                        } else if (Maps.nonUnaryMnemonTable.containsKey(
                                tempStr.toLowerCase())) {
                            localMnemon = Maps.nonUnaryMnemonTable.get(
                                    tempStr.toLowerCase());
                            //aCode = new NonUnaryInstr(localMnemon);
                            state = ParseState.PS_NONUNARY;
                        } else { //change identifier error message
                            aCode = new Error("Illegal mnemonic");
                        }
                    } else if (aToken instanceof TDot) { //add DotInstr
                        TDot localTDot = (TDot) aToken;
                        String tempStr = localTDot.getStringValue();
                        if (Maps.dotMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.dotMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_DOT;
                        } else {
                            aCode = new Error("Illegal dot command");
                        }
                    }  else { //change default error message
                        aCode = new Error("Must have a mnemonic or dot command after symbol definition.");
                    }
                    break;
                case PS_UNARY:
                    if (aToken instanceof TEmpty) {
                        //aCode = new EmptyInstr();
                        aCode = new UnaryInstr(symbol,localMnemon, increment);
                        terminate = localMnemon == Mnemon.M_END;
                        state = ParseState.PS_FINAL;
                        increment++;
                    } else if (aToken instanceof TComment){
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        aCode = new UnaryInstr(symbol,localMnemon, increment,tempStr);
                        terminate = localMnemon == Mnemon.M_END;
                        state = ParseState.PS_FINAL;
                        increment++;
                    }else {
                        aCode = new Error("Extra input found");
                    }
                    break;
                case PS_NONUNARY:
                    if (aToken instanceof TInteger) {
                        TInteger localTInteger = (TInteger) aToken;
                        Integer tempInt = localTInteger.getIntValue();
                        address = new DecArg(tempInt); //sets address to a new decimal

                        state = ParseState.PS_CONST;
                    } else if (aToken instanceof THex) {
                        THex localTHex = (THex) aToken;
                        Integer tempInt = localTHex.getHexValue();
                        address = new HexArg(tempInt); //sets address to a new hex

                        state = ParseState.PS_CONST;
                    } else if (aToken instanceof TIdentifier) {
                        TIdentifier localTString = (TIdentifier) aToken;
                        String tempStr = localTString.getStringValue();
                        if(SymbolMaps.symbolValueTable.containsKey(tempStr)) {
                            address = new SymArg(tempStr);
                            state = ParseState.PS_CONST;
                        } else {
                            aCode = new Error("Illegal symbol");
                        }
                    } else {
                        aCode = new Error("Illegal address");
                    }
                    break;
                case PS_DOT: //either .block or .end
                    if (aToken instanceof TEmpty) {
                        if(localMnemon == Mnemon.M_BLOCK){
                            aCode = new Error(".BLOCK requires a decimal or hex constant argument");
                        } else {
                            aCode = new DotInstr(symbol,localMnemon, increment);
                            increment++;
                            state = ParseState.PS_FINAL;
                            terminate = localMnemon == Mnemon.M_END;
                        }
                    } else if (aToken instanceof TComment){
                        TComment localTComment = (TComment) aToken;
                        if (localMnemon == Mnemon.M_BLOCK){
                            aCode = new Error(".BLOCK requires a decimal or hex constant argument");
                        } else {
                            String tempStr = localTComment.getStringValue();
                            aCode = new DotInstr(symbol,localMnemon, increment,tempStr);
                            increment++;
                            state = ParseState.PS_FINAL;
                            terminate = localMnemon == Mnemon.M_END;
                        }
                    }else if (aToken instanceof THex && localMnemon == Mnemon.M_BLOCK) {
                        THex localTHex = (THex) aToken;
                        Integer tempInt = localTHex.getHexValue();
                        address = new HexArg(tempInt);
                        state = ParseState.PS_DOTCONST;
                    } else if (aToken instanceof TInteger && localMnemon == Mnemon.M_BLOCK) {
                        TInteger localTInteger = (TInteger) aToken;
                        Integer tempInt = localTInteger.getIntValue();
                        if (tempInt < 0){
                            aCode = new Error("BLOCK cannot use negative numbers");
                        } else {
                            address = new DecArg(tempInt);
                            state = ParseState.PS_DOTCONST;
                        }
                    } else if (aToken instanceof TIdentifier && localMnemon == Mnemon.M_BLOCK) {
                        TIdentifier localTString = (TIdentifier) aToken;
                        String tempStr = localTString.getStringValue();
                        if(SymbolMaps.symbolValueTable.containsKey(tempStr)) {
                            address = new SymArg(tempStr);
                            state = ParseState.PS_DOTCONST;
                        } else {
                            aCode = new Error("Illegal symbol");
                        }
                    }else {
                        aCode = new Error("Illegal constant");
                    }
                    break;
                case PS_CONST:
                    if(aToken instanceof TAddress){ //if token gets memory address
                        TAddress localTAddress = (TAddress) aToken;
                        String tempStr = localTAddress.getStringValue();
                        if (Maps.addrMnemonTable.containsKey(tempStr.toLowerCase())) {
                            addrMode = Maps.addrMnemonTable.get(tempStr.toLowerCase());
                            //filters out mnemonics that cannot use immediate addressing: DECI & STWA
                            if(addrMode == Mnemon.M_IMM && (localMnemon == Mnemon.M_DECI || localMnemon == Mnemon.M_STWA)){
                                aCode = new Error("Illegal addressing mode");
                            } else {
                                terminate = localMnemon == Mnemon.M_END;
                                state = ParseState.PS_ADDR;
                            }
                        } else {
                            aCode = new Error("Illegal addressing mode");
                        }
                    } else if (aToken instanceof TComment) {
                        if(localMnemon == Mnemon.M_BR || localMnemon == Mnemon.M_BRLT || localMnemon == Mnemon.M_BREQ || localMnemon == Mnemon.M_BRLE) {
                            TComment localTComment = (TComment) aToken;
                            String tempStr = localTComment.getStringValue();
                            aCode = new NonUnaryInstr(symbol, localMnemon, address, addrMode, increment, tempStr);
                            state = ParseState.PS_FINAL;
                            increment = increment + 3;
                        } else{
                            aCode = new Error("Addressing mode required");
                        }
                    } else if (aToken instanceof TEmpty) {
                        if(localMnemon == Mnemon.M_BR || localMnemon == Mnemon.M_BRLT || localMnemon == Mnemon.M_BREQ || localMnemon == Mnemon.M_BRLE) {
                            aCode = new NonUnaryInstr(symbol, localMnemon, address, addrMode, increment);
                            state = ParseState.PS_FINAL;
                            increment = increment + 3;
                        } else{
                            aCode = new Error("Addressing mode required");
                        }
                    } else {
                        aCode = new Error("Addressing mode required");
                    }
                    break;
                case PS_DOTCONST: //only affects .block
                    if (aToken instanceof TEmpty){
                        aCode = new DotInstr(symbol,localMnemon, address, increment);
                        state = ParseState.PS_FINAL;
                        increment = increment + (address.getValue());
                    } else if (aToken instanceof TComment) {
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        aCode = new DotInstr(symbol,localMnemon, address, increment, tempStr);
                        state = ParseState.PS_FINAL;
                        increment = increment + (address.getValue());
                    } else if (aToken instanceof TAddress){
                        TAddress localTAddress = (TAddress) aToken;
                        String tempStr = localTAddress.getStringValue();
                        if (Maps.addrMnemonTable.containsKey(tempStr.toLowerCase())){
                            if(Maps.addrMnemonTable.get(tempStr.toLowerCase()) == Mnemon.M_DIR){
                                aCode = new DotInstr(symbol,localMnemon, address, increment);
                                state = ParseState.PS_FINAL;
                                increment = increment + (address.getValue());
                            } else {
                                aCode = new Error("Illegal addressing mode");
                            }
                        } else {
                            aCode = new Error("Invalid Syntax");
                        }
                    }
                    break;
                case PS_ADDR:
                    if (aToken instanceof TComment) {
                        TComment localTComment = (TComment) aToken;
                        String tempStr = localTComment.getStringValue();
                        aCode = new NonUnaryInstr(symbol,localMnemon, address, addrMode, increment, tempStr);
                        state = ParseState.PS_FINAL;
                        increment = increment + 3;
                    } else if (aToken instanceof TEmpty) {
                        aCode = new NonUnaryInstr(symbol,localMnemon, address, addrMode, increment);
                        state = ParseState.PS_FINAL;
                        increment = increment + 3;
                    } else {
                        aCode = new Error("Syntax Error");
                    }
                    break;
                case PS_SYMBOL:
                    break;
                /**case PS_NEWSYMBOL: //adds new symbol to list of symbol tables, along with respective symbol index
                    TSymbol localTSymbol = (TSymbol) aToken;
                    String localStr = localTSymbol.getStringValue();
                    //Symbols.symbolTable.put(localStr, increment);
                    state = ParseState.PS_START;
                    break;
                 */
            }
        } while (state != ParseState.PS_FINAL && !(aCode instanceof Error));
        return terminate;
    }
    public void translate() {
        ArrayList<ACode> codeTable = new ArrayList<>();
        symToken = new Tokenizer(symBuffer);
        symBuffer.getLine();
        while(symBuffer.inputRemains()){
            symbolParse();
            symBuffer.getLine();
        }
        int numErrors = 0;
        t = new Tokenizer(b);
        boolean terminateWithEnd = false;
        b.getLine();
        while (b.inputRemains() && !terminateWithEnd) {
            terminateWithEnd = parseLine(); // Sets aCode and returns boolean.
            codeTable.add(aCode);
            if (aCode instanceof Error) {
                numErrors++;
            }
            b.getLine();
        }
        if (!terminateWithEnd) {
            aCode = new Error("Missing \".END\" sentinel.");
            codeTable.add(aCode);
            numErrors++;
        }
        if (numErrors == 0) { //uncomment once generateCode() is completed
            System.out.printf("Object code:\n----------------------------------------\n\n");
            for (int i = 0; i < codeTable.size(); i++) {
                System.out.printf("%s", codeTable.get(i).generateCode());
            }
        }
        if (numErrors == 1) {
            System.out.printf("One error was detected.\n");
        } else if (numErrors > 1) {
            System.out.printf("%d errors were detected.\n", numErrors);
        }
        System.out.printf("\n\nProgram listing:\n-----------------------------------------------------\nAddr  Symbol   Mnemon  Operand     Comment\n-----------------------------------------------------\n \n");
        for (int i = 0; i < codeTable.size(); i++) {
            System.out.printf("%s", codeTable.get(i).generateListing());
        }
    }
}