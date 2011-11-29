package org.iocaste.external;

import com.sun.jna.Library;

public interface ExternalInterface extends Library {

    public ExternalViewData init_view(String viewname, String path);
}
