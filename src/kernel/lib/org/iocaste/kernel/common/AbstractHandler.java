package org.iocaste.kernel.common;

public abstract class AbstractHandler implements Handler {
    private Function function;
    
    @Override
    public abstract Object run(Message message) throws Exception;

    @SuppressWarnings("unchecked")
    protected <T extends Function> T getFunction() {
        return (T)function;
    }
    
    @Override
    public void setFunction(Function function) {
        this.function = function;
    }

}
