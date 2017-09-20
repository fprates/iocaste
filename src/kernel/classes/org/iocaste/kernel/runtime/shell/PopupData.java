package org.iocaste.kernel.runtime.shell;

import java.sql.Connection;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.PopupControl;

public class PopupData {
    public PopupControl control;
    public String action, form;
    public ViewContext viewctx;
    public Container container;
    public Connection connection;
}
