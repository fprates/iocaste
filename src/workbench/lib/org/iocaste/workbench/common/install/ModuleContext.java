package org.iocaste.workbench.common.install;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.utils.ConversionRules;
import org.iocaste.protocol.utils.XMLConversion;

public class ModuleContext {
    public Map<String, InstallModule> modules;
    public XMLConversion conversion;
    public ConversionRules mapping;
    public StandardInstallContext context;
    public PageBuilderDefaultInstall defaultinstall;
    
    public ModuleContext(Function function) {
        modules = new LinkedHashMap<>();
        conversion = new XMLConversion(function);
        mapping = new ConversionRules();
    }
}
