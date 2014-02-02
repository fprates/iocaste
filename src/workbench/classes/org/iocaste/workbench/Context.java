package org.iocaste.workbench;

import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.PageContext;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public class Context extends PageContext {
    public static final byte NEW = 0;
    public static final byte EDIT = 1;
    public byte editormode;
    public String repository;
    public TextEditorTool tetool;
    public TextEditor editor;
    
    public String projectname;
    public String projectdir;
    public String projectsourceobj;
    public String projectfullsourcename;
    public String projectsourcename;
    public String projectdefsource;
    public long projectpackageid;
    public long projectsourceid;
    public long projectlastsrcid;
    public Map<String, ExtendedObject> projectsources;
}