package org.iocaste.install;

import org.iocaste.internal.AbstractRenderer;

public class InstallPageRenderer extends AbstractRenderer {
    private static final long serialVersionUID = -8143025594178489781L;

    static {
        reqproc = new ProcessInstallHttpRequisition();
    }
}