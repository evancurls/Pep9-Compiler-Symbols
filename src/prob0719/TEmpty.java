package prob0719;

/**
 * Inheritance class of AToken, overrides description given Token is Empty.
 *
 * <p>
 * File: <code>TEmpty.java</code>
 *
 * @author Inrig
 */

public class TEmpty extends AToken {
    @Override
    public String getDescription() {
        return "Empty token";
    }
}