package org.iocaste.external.service;

public class ExternalProperty implements ExternalPropertyProtocol {
    private String name;
    private ExternalTypes type;
    private Object value;
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getBoolean()
     */
    @Override
    public final boolean getBoolean() {
        return (type == ExternalTypes.BOOLEAN)?(Boolean)value : false;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getChar()
     */
    @Override
    public final char getChar() {
        return (type == ExternalTypes.CHAR)?(Character)value : '\0';
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getDouble()
     */
    @Override
    public final double getDouble() {
        return (type == ExternalTypes.DOUBLE)?(Double)value : 0;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getFloat()
     */
    @Override
    public final float getFloat() {
        return (type == ExternalTypes.FLOAT)?(Float)value : 0;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getInt()
     */
    @Override
    public final int getInt() {
        return (type == ExternalTypes.INTEGER)?(Integer)value : 0;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getLong()
     */
    @Override
    public final long getLong() {
        return (type == ExternalTypes.LONG)?(Long)value : 0;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getMessage()
     */
    @Override
    public final ExternalMessageProtocol getMessage() {
        return (type == ExternalTypes.MESSAGE)?(ExternalMessage)value : null;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public final Object getValue() {
        return value;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getProperty()
     */
    @Override
    public final ExternalPropertyProtocol getProperty() {
        return (type == ExternalTypes.PROPERTY)?(ExternalProperty)value : null;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getShort()
     */
    @Override
    public final short getShort() {
        return (type == ExternalTypes.SHORT)?(Short)value : 0;
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getString()
     */
    @Override
    public final String getString() {
        return (type == ExternalTypes.STRING)?(String)value : "";
    }
    
    /* (non-Javadoc)
     * @see org.iocaste.external.service.ExternalPropertyProtocol#getType()
     */
    @Override
    public final ExternalTypes getType() {
        return type;
    }
    
    /**
     * @param name the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param type the type to set
     */
    public final void setType(ExternalTypes type) {
        this.type = type;
    }
    
    /**
     * @param value the value to set
     */
    public final void setValue(Object value) {
        this.value = value;
    }
}
