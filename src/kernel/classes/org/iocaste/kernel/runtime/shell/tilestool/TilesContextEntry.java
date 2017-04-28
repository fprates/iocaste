package org.iocaste.kernel.runtime.shell.tilestool;

import java.util.ArrayList;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.AbstractContextEntry;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public class TilesContextEntry extends AbstractContextEntry {
    public String action;
    public Object value;
    
    public TilesContextEntry() {
        super(TYPES.TILES);
        set(new ArrayList<ExtendedObject>());
    }
}
