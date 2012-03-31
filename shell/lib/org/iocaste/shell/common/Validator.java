package org.iocaste.shell.common;

import java.io.Serializable;

import org.iocaste.protocol.Function;

public interface Validator extends Serializable {

    public abstract void setFunction(Function function);
    
    public abstract String validate(ValidatorConfig config) throws Exception;
}
