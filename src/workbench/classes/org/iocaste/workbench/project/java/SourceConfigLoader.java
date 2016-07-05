package org.iocaste.workbench.project.java;

import java.util.Map;

import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class SourceConfigLoader implements ItemLoader {
    
    @Override
    public void execute(ViewerItemPickData pickdata, Context excontext) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init(Context extcontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("class", pickdata.value);
        parameters.put("package", extcontext.pkgitem.getstKey());
    }
    
}