package org.iocaste.shell.common;

import java.io.Serializable;

public class HeaderLink implements Serializable {
    private static final long serialVersionUID = -3763911639060405285L;
    public String rel, type, href;
    
    public HeaderLink(String rel, String href) {
        this(rel, null, href);
    }
    
    public HeaderLink(String rel, String type, String href) {
        this.rel = rel;
        this.type = type;
        this.href = href;
    }
}
