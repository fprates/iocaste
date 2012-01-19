package org.iocaste.external.service;

public interface ExternalPropertyProtocol {

    /**
     * 
     * @return
     */
    public abstract boolean getBoolean();

    /**
     * 
     * @return
     */
    public abstract char getChar();

    /**
     * 
     * @return
     */
    public abstract double getDouble();

    /**
     * 
     * @return
     */
    public abstract float getFloat();

    /**
     * 
     * @return
     */
    public abstract int getInt();

    /**
     * 
     * @return
     */
    public abstract long getLong();

    /**
     * 
     * @return
     */
    public abstract ExternalMessageProtocol getMessage();

    /**
     * @return the name
     */
    public abstract String getName();

    /**
     * 
     * @return
     */
    public abstract ExternalPropertyProtocol getProperty();

    /**
     * 
     * @return
     */
    public abstract short getShort();

    /**
     * @return the value
     */
    public abstract String getString();

    /**
     * @return the type
     */
    public abstract ExternalTypes getType();

}