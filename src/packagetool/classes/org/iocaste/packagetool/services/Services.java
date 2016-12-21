package org.iocaste.packagetool.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.installers.AuthorizationInstaller;
import org.iocaste.packagetool.services.installers.ComplexModelInstaller;
import org.iocaste.packagetool.services.installers.GlobalConfigInstaller;
import org.iocaste.packagetool.services.installers.ModelInstaller;
import org.iocaste.packagetool.services.installers.ModuleInstaller;
import org.iocaste.packagetool.services.installers.NumberFactoryInstaller;
import org.iocaste.packagetool.services.installers.PackageInstaller;
import org.iocaste.packagetool.services.installers.SearchHelpInstaller;
import org.iocaste.packagetool.services.installers.UserProfileInstaller;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    public Map<String, ModuleInstaller> installers;

    public Services() {
        export("assign_task_group", "assignTaskGroup");
        export("install", new PackageInstall());
        export("is_installed", new IsInstalled());
        export("uninstall", new Uninstall());
        export("update", new PackageUpdate());
        
        installers = new LinkedHashMap<>();
        new ModelInstaller(this);
        new ComplexModelInstaller(this);
//        new UsersInstaller(installers);
        new NumberFactoryInstaller(this);
        new SearchHelpInstaller(this);
        new AuthorizationInstaller(this);
        new UserProfileInstaller(this);
//        new LinkInstaller(installers);
//        new TaskGroupInstaller(installers);
//        new TaskGroupItemInstaller(installers);
        new GlobalConfigInstaller(this);
//        new TextInstaller(installers);
        new PackageInstaller(this);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assignTaskGroup(Message message) throws Exception {
        ExtendedObject group;
        String groupname = message.getst("group");
        String username = message.getst("username");
        State state = new State();
        
        state.documents = new Documents(this);
        group = Selector.getGroup(groupname, state);
        Selector.assignGroup(group, username, state);
    }
}
