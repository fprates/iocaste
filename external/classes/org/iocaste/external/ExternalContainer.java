package org.iocaste.external;

import com.sun.jna.Structure;

public class ExternalContainer extends Structure {
    public ExternalElement base;
    public ExternalElement[] elements;
}
