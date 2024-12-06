package prob0719;

/**
 * Basic Empty Instruction Inheritance Class
 *
 * <p>
 * File: <code>EmptyInstr.java</code>
 *
 * @author Inrig
 */

public class EmptyInstr extends ACode {
    // For an empty source line.
    @Override
    public String generateListing() {
        return "\n";
    }
    @Override
    public String generateCode() {
        return "";
    }
}
