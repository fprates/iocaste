package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.workbench.project.ProjectAdd;
import org.iocaste.workbench.project.ProjectInfo;
import org.iocaste.workbench.project.ProjectUse;
import org.iocaste.workbench.project.compile.Compile;
import org.iocaste.workbench.project.datadict.DataElementAdd;
import org.iocaste.workbench.project.datadict.DataElementRemove;
import org.iocaste.workbench.project.datadict.ModelAdd;
import org.iocaste.workbench.project.datadict.ModelItemAdd;
import org.iocaste.workbench.project.datadict.ModelItemRemove;
import org.iocaste.workbench.project.datadict.ModelRemove;
import org.iocaste.workbench.project.datadict.ModelUse;
import org.iocaste.workbench.project.java.ClassEditorCall;
import org.iocaste.workbench.project.java.ClassRemove;
import org.iocaste.workbench.project.java.PackageAdd;
import org.iocaste.workbench.project.java.PackageRemove;
import org.iocaste.workbench.project.java.editor.ClassEditorContext;
import org.iocaste.workbench.project.tasks.LinkAdd;
import org.iocaste.workbench.project.tasks.LinkRemove;
import org.iocaste.workbench.project.view.ViewAdd;
import org.iocaste.workbench.project.view.ViewConfigEdit;
import org.iocaste.workbench.project.view.ViewConfigRemove;
import org.iocaste.workbench.project.view.ViewRemove;
import org.iocaste.workbench.project.view.ViewSpecAdd;
import org.iocaste.workbench.project.view.ViewSpecRemove;
import org.iocaste.workbench.project.view.ViewUse;
import org.iocaste.workbench.project.view.action.ActionAdd;
import org.iocaste.workbench.project.view.config.DataFormConfig;
import org.iocaste.workbench.project.view.config.InputComponentConfig;
import org.iocaste.workbench.project.view.config.TableToolConfig;
import org.iocaste.workbench.project.view.config.ViewConfigContext;
import org.iocaste.workbench.project.view.toolitem.ToolItemAdd;
import org.iocaste.workbench.project.view.toolitem.ToolItemRemove;

public class Context extends AbstractExtendedContext {
    public Map<String, AbstractCommand> commands;
    public ComplexDocument project, model, view, pkgitem, spec;
    public ViewConfigContext viewconfig;
    public ClassEditorContext classeditor;
    public ProjectInfo[] projects;
    public Object callreturn;
    public String titlearg;
    public Map<String, ActionContext> actions;
    
    public Context(PageBuilderContext context) {
        super(context);
        
        commands = new HashMap<>();
        classeditor = new ClassEditorContext(context, this);
        actions = new HashMap<>();
        
        new ActionAdd(this);
        new ClassEditorCall("class-add", this);
        new ClassEditorCall("class-edit", this);
        new ClassRemove(this);
        new Compile(this);
        new DataElementAdd(this);
        new DataElementRemove(this);
        new LinkAdd(this);
        new LinkRemove(this);
        new ModelAdd(this);
        new ModelItemAdd(this);
        new ModelItemRemove(this);
        new ModelUse(this);
        new ModelRemove(this);
        new PackageAdd(this);
        new PackageRemove(this);
        new ProjectAdd(this);
        new ProjectUse(this);
        new ToolItemAdd(this);
        new ToolItemRemove(this);
        new ViewAdd(this);
        new ViewRemove(this);
        new ViewUse(this);
        new ViewConfigEdit(this);
        new ViewConfigRemove(this);
        new ViewSpecRemove(this);
        new ViewSpecAdd(this);
        
        viewconfig = new ViewConfigContext();
        viewconfig.extcontext = this;
        new InputComponentConfig(viewconfig, ViewSpecItem.TYPES.TEXT_FIELD);
        new DataFormConfig(viewconfig);
        new TableToolConfig(viewconfig);
    }
}
