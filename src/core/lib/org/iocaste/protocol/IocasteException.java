package org.iocaste.protocol;

public class IocasteException extends Exception {
    private static final long serialVersionUID = -3393799052504772427L;

    public IocasteException(String format, Object... args) {
        super(String.format(format, args));
    }
}
