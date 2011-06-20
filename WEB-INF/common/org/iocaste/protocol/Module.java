package org.iocaste.protocol;

import java.io.Serializable;

public interface Module extends Serializable {
    public abstract Service serviceInstance(String path) throws Exception;
}
