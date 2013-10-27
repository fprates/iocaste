package org.iocaste.workbench;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.common.PageContext;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.project.ProjectData;

public class Context extends PageContext {
    public static final byte CREATE = 0;
    public static final byte LOAD = 1;
    public static final String[] TITLES = {
        "project.create",
        "project.update"
    };
    
    public ProjectData project;
    public String repository, path;
    public boolean validrepo;
    public DocumentModel editorhdrmodel, projectmodel, packagemodel;
    public DocumentModel projectnamemodel, sourcemodel, srccodemodel;
    public DocumentModel installmodel;
    public byte mode;
    public TextEditorTool editor;
}
