package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class ReportToolData extends AbstractComponentData {
    private static final byte INPUT = 0;
    private static final byte OUTPUT = 1;
    private byte stage;
    public String nsreference;
    public ReportToolStage input, output;
    
    public ReportToolData(PageBuilderContext context) {
        super(ViewSpecItem.TYPES.REPORT_TOOL);
        this.context = context;
        stage = OUTPUT;
        input = new ReportToolStage();
        output = new ReportToolStage();
    }
    
    public final void doInput() {
        stage = INPUT;
    }
    
    public final void doOutput() {
        stage = OUTPUT;
    }
    
    public final boolean isInput() {
        return (stage == INPUT);
    }

    @Override
    public <T extends AbstractComponentDataItem> T instance(String name) {
        return null;
    }
}
