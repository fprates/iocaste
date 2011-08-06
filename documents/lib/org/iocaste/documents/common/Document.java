package org.iocaste.documents.common;

import java.io.Serializable;

public interface Document extends Serializable {

    public abstract DataComponent[] getComponents();
}
