package org.quantic.iocasteconnector;

public class Command {
    public enum Parameters {
        NEXT, USER, HOST, LOCALE, PORT, SECRET
    };
    
    public int error;
    public String secret, user, message, host, locale, port;
    public Parameters type;
}
