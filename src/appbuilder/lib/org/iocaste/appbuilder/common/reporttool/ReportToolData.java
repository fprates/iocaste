package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;

public class ReportToolData {
    private static final byte INPUT = 0;
    private static final byte OUTPUT = 1;
    private byte stage;
    public AbstractContext context;
    public String name, model;
    public ExtendedObject[] objects;
    public ReportToolStage input, output;
    
    public ReportToolData(AbstractContext context, String name) {
        this.context = context;
        this.name = name;
        input = new ReportToolStage(this);
        output = new ReportToolStage(this);
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
}
