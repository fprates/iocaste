package org.iocaste.appbuilder.common.tiles;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;

public interface TileRenderer {

    public abstract void run(Container container, ExtendedObject object);
}
