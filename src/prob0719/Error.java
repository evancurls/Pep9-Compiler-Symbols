package prob0719;

/**
 * Error Formulation.
 *
 * <p>
 * File: <code>Error.java</code>
 *
 * @author Inrig
 */

public class Error extends ACode {
    private final String errorMessage;
    public Error(String errMessage) {
        errorMessage = errMessage;
    }
    @Override
    public String generateListing() {
        return "ERROR: " + errorMessage + "\n";
    }
    @Override
    public String generateCode() {
        return "";
    }
}
