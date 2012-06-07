package org.iocaste.shell.common;

import org.iocaste.protocol.Function;

public abstract class AbstractValidator implements Validator {
    private static final long serialVersionUID = 1174080367157084461L;
    private Function function;
    
    /**
     * 
     * @return
     */
    protected final Function getFunction() {
        return function;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#setFunction(
     *     org.iocaste.protocol.Function)
     */
    @Override
    public final void setFunction(Function function) {
        this.function = function;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Validator#validate(
     *     org.iocaste.shell.common.ValidatorConfig)
     */
    @Override
    public abstract void validate(ValidatorConfig config) throws Exception;

}
