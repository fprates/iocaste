package org.iocaste.runtime.common.navcontrol;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewInput;
import org.iocaste.shell.common.ViewTitle;

public class StandardNavControlInput extends AbstractViewInput<Context> {

	@Override
	protected void execute(Context context) {
        ViewTitle title = context.getPage().getTitle();
        
		textset("this", (title.text != null)?
        		title.text : (title.text = context.getAppName()), title.args);
	}

	@Override
	protected void init(Context context) {
		execute(context);
	}
	
}