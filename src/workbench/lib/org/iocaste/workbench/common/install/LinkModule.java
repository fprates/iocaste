package org.iocaste.workbench.common.install;

import org.iocaste.protocol.utils.ConversionResult;

public class LinkModule extends AbstractInstallModule {
    
    public LinkModule(ModuleContext context) {
        super(context, "install.links", "link");
    }
    
    @Override
    protected final void execute(ConversionResult map) {
        String name = map.getst("install.links.link.name");
        String program = map.getst("install.links.link.command");
        String group = map.getst("install.links.link.group");
        modulectx.defaultinstall.setLink(name, program);
        if (group != null)
            modulectx.defaultinstall.addToTaskGroup(group, name);
    }
}
