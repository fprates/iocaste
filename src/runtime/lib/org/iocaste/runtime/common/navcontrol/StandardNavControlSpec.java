package org.iocaste.runtime.common.navcontrol;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.AbstractViewSpec;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public class StandardNavControlSpec extends AbstractViewSpec {
	public Object[][] ncspec;
    private static final Map<String, TYPES> SPEC_ITEM_TYPES;
    
    static {
        SPEC_ITEM_TYPES = new HashMap<>();
        for (TYPES type : TYPES.values())
            SPEC_ITEM_TYPES.put(type.name(), type);
    }
	
    @Override
    protected void execute(Context context) {
        ViewSpecItem.TYPES type;
        String parent, name;
        AbstractPage page;
        
        for (int i = 0; i < ncspec.length; i++) {
            type = SPEC_ITEM_TYPES.get(ncspec[i][0]);
            parent = (String)ncspec[i][1];
            name = (String)ncspec[i][2];
            component(type, parent, name);
        }
        
        page = context.getPage();
        specBar(page);
        for (String parentkey : page.getChildren())
            specBar(page.getChild(parentkey));
    }
    
    private final void specBar(AbstractPage page) {
        String name;
        for (String key : page.getActions())
            button("actionbar", key);
        name = page.getSubmit();
        if (name != null)
            button("actionbar", name);
    }
}