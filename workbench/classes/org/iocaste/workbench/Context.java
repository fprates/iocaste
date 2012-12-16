package org.iocaste.workbench;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.View;

public class Context {
    public static final byte CREATE = 0;
    public static final byte LOAD = 1;
    public static final String[] TITLES = {
        "project.create",
        "project.update"
    };
    public View view;
    public Function function;
    public Project project;
    public String repository;
    public boolean validrepo;
    public DocumentModel editorhdrmodel, projectmodel, packagemodel;
    public DocumentModel sourcemodel, srccodemodel;
    public byte mode;
}
