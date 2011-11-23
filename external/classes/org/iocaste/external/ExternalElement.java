package org.iocaste.external;

import com.sun.jna.Structure;
import com.sun.jna.Union;

public class ExternalElement extends Structure {
    public String name;
    public String type;
    public boolean iscontainable;
    public ExternalContainer container;
    public Union child;
}
