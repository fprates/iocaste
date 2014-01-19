package org.iocaste.examples;

import org.iocaste.report.common.Report;
import org.iocaste.report.common.ReportParameters;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.PageContext;

public class Request {
    
    public static final void pinggo(PageContext context) {
        Examples examples = new Examples(context.function);
        
        for(int i = 0; i < 10000; i++)
            examples.ping();
        
        context.view.message(Const.STATUS, "successfully.pinged");
    }

    public static final byte[] report(PageContext context) {
        Report report = new Report(context.function);
        ReportParameters parameters = new ReportParameters();
        
        parameters.setContentFormat(ReportParameters.PDF);
        
        return report.getContent(parameters);
    }
}
