package org.iocaste.shell.common;

import java.io.Serializable;

public class ViewState implements Serializable {
    private static final long serialVersionUID = 3411135941186033095L;
    public View view;
    public boolean keepview, reloadable;
}
