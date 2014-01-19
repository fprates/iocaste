package org.iocaste.protocol;

public class InvalidSessionException extends Exception {
    private static final long serialVersionUID = -3393799052504772427L;

    public InvalidSessionException() {
        super("Invalid session.");
    }
}
