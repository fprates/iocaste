package org.iocaste.runtime.common.navcontrol;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.AbstractViewSpec;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public class StandardNavControlSpec extends AbstractViewSpec {
	public Object[][] ncspec;
	
    @Override
    protected void execute(Context context) {
        ViewSpecItem.TYPES type;
        String parent, name;
        AbstractPage page;
        
        for (int i = 0; i < ncspec.length; i++) {
            type = (TYPES)ncspec[i][0];
            parent = (String)ncspec[i][1];
            name = (String)ncspec[i][2];
            component(type, parent, name);
        }
        
        page = context.getPage();
        for (String key : page.getActions())
            button("actionbar", key);
        
        name = page.getSubmit();
        if (name != null)
            button("actionbar", name);
    }
}