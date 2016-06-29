package org.iocaste.workbench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.workbench.project.ProjectAdd;
import org.iocaste.workbench.project.ProjectList;
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
import org.iocaste.workbench.project.java.PackageAdd;
import org.iocaste.workbench.project.java.editor.ClassEditorContext;
import org.iocaste.workbench.project.tasks.LinkAdd;
import org.iocaste.workbench.project.tasks.LinkRemove;
import org.iocaste.workbench.project.view.ViewAdd;
import org.iocaste.workbench.project.view.ViewConfigEdit;
import org.iocaste.workbench.project.view.ViewRemove;
import org.iocaste.workbench.project.view.ViewSpecAdd;
import org.iocaste.workbench.project.view.ViewSpecRemove;
import org.iocaste.workbench.project.view.ViewUse;
import org.iocaste.workbench.project.view.action.ActionAdd;
import org.iocaste.workbench.project.view.config.DataFormConfig;
import org.iocaste.workbench.project.view.config.TableToolConfig;
import org.iocaste.workbench.project.view.config.ViewConfigContext;

public class Context extends AbstractExtendedContext {
    public Map<String, AbstractCommand> commands;
    public List<String> output;
    public ComplexDocument project, model, view;
    public ViewConfigContext viewconfig;
    public ClassEditorContext classeditor;
    
    public Context(PageBuilderContext context) {
        super(context);
        String name;
        
        output = new ArrayList<>();
        commands = new HashMap<>();
        commands.put("action-add", new ActionAdd());
        commands.put("class-add", new ClassEditorCall());
        commands.put("compile", new Compile(context));
        commands.put("data-element-add", new DataElementAdd());
        commands.put("data-element-remove", new DataElementRemove());
        commands.put("link-add", new LinkAdd());
        commands.put("link-remove", new LinkRemove());
        commands.put("model-add", new ModelAdd());
        commands.put("model-item-add", new ModelItemAdd());
        commands.put("model-item-remove", new ModelItemRemove());
        commands.put("model-use", new ModelUse());
        commands.put("model-remove", new ModelRemove());
        commands.put("package-add", new PackageAdd());
        commands.put("project-add", new ProjectAdd());
        commands.put("project-list", new ProjectList());
        commands.put("project-use", new ProjectUse());
        commands.put("view-add", new ViewAdd());
        commands.put("view-remove", new ViewRemove());
        commands.put("view-use", new ViewUse());
        commands.put("viewconfig", new ViewConfigEdit());
        commands.put("viewspec-remove", new ViewSpecRemove());
        
        for (ViewSpecItem.TYPES type : ViewSpecItem.TYPES.values()) {
            name = String.format("viewspec-%s", type.toString());
            commands.put(name, new ViewSpecAdd(type));
        }
        
        viewconfig = new ViewConfigContext();
        viewconfig.extcontext = this;
        new DataFormConfig(viewconfig);
        new TableToolConfig(viewconfig);
        
        classeditor = new ClassEditorContext();
    }
}
