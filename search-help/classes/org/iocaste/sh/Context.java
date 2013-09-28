package org.iocaste.sh;

import java.util.Map;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.PageContext;

public class Context extends PageContext {
    public Map<String, ValueRange> criteria;
}
