package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.documents.common.Documents;
import org.iocaste.examples.Context;

public class DataFormUsePageInput extends StandardViewInput {

    @Override
    public final void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        super.execute(context);
        if (Documents.isInitial(extcontext.dfuse.object))
            textset("dfuse_output", "dfuse_output_fail");
        else
            textset("dfuse_output", "dfuse_output_ok",
                    extcontext.dfuse.object.getst("GIFT"),
                    extcontext.dfuse.object.getst("NAME"),
                    extcontext.dfuse.object.getst("ADJECTIVE"));
    }
}
