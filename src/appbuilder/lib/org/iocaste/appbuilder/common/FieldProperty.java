package org.iocaste.appbuilder.common;
import java.io.Serializable;
import java.util.Map;

import org.iocaste.shell.common.Const;


public class FieldProperty implements Serializable {
    private static final long serialVersionUID = 5649667185035050916L;

    public Const type;
    public Map<String, Object> values;
}
