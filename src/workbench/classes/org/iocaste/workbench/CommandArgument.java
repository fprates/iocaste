package org.iocaste.workbench;

public class CommandArgument {
    public byte type;
    public String field;
    public boolean bool;
    
    public CommandArgument(byte type, String field) {
        this.type = type;
        this.field = field;
    }
}