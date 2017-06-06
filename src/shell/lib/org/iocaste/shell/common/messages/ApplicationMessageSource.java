package org.iocaste.shell.common.messages;

import org.iocaste.shell.common.AbstractContext;

public interface ApplicationMessageSource {
    
    /**
     * 
     * @return
     */
    public abstract <T extends AbstractContext> T configOnly() throws Exception;

}
