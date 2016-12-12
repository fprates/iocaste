package org.iocaste.shell;

import org.iocaste.internal.AbstractRenderer;
import org.iocaste.internal.ProcessHttpRequisition;

public class PageRenderer extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;

    static {
        reqproc = new ProcessHttpRequisition();
    }
}