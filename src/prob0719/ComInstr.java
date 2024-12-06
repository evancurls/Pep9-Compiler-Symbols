package prob0719;

/**
 * Comment Based Instruction, only includes comment
 *
 * <p>
 * File: <code>UnaryInstr.java</code>
 *
 * @author Inrig
 */

public class ComInstr extends ACode {
    private final String comment;
    public ComInstr(String com) {
        comment = com;
    }

    @Override
    public String generateListing() {
            return String.format("        ;%s\n", comment);
    }
    @Override
    public String generateCode() {
        return String.format("");
    }
}