package org.iocaste.report.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.report.common.data.ReportViewerData;
import org.iocaste.shell.common.Const;

public abstract class AbstractReportSelect extends AbstractActionHandler {
    private ReportViewerData data;
    private DocumentModel outputmodel;
    private AbstractReportContext extcontext;
    private Map<String, Map<Object, ExtendedObject>> cache;
    
    public AbstractReportSelect() {
        cache = new HashMap<>();
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ReportToolData rtdata;
        
        rtdata = new ReportToolData(context);
        rtdata.name = "output";
        extcontext = getExtendedContext();
        if (data.input.name != null)
            extcontext.object = reportinputget("head");
        preselect(context);
        if (context.function.getMessageType() == Const.ERROR)
            return;
        
        data.output.config.config(context, rtdata.output);
        outputmodel = ReportTool.buildModel(rtdata);
        extcontext.items.clear();
        cache.clear();
        select(context);
        
        if (extcontext.items.size() == 0) {
            message(Const.ERROR, "no.match");
            return;
        }
        
        if (data.dataonly)
            return;
        
        init(data.output.name, extcontext);
        redirect(data.output.name);
    }
    
    protected String getText(String managername,
            ComplexDocument document, String itemsname, String keyfield,
            String textfield, String key) {
        Map<Object, ExtendedObject> objects, texts;
        
        texts = cache.get(managername);
        if (texts == null) {
            texts = new HashMap<>();
            cache.put(managername, texts);
        }
        
        if (!texts.containsKey(key)) {
            objects = getRelated(document, itemsname, keyfield);
            texts.putAll(objects);
        }
        
        return texts.get(key).getst(textfield);
    }
    
    protected final ExtendedObject outputLineInstance() {
        ExtendedObject object = new ExtendedObject(outputmodel);
        extcontext.items.add(object);
        return object;
    }
    
    protected void preselect(PageBuilderContext context) { }
    
    protected final ExtendedObject reportinputget(String report) {
        ReportToolData rtdata = data.context.getView().getComponents().
                getComponentData(report);
        if (rtdata == null)
            throw new RuntimeException(new StringBuilder(report).
                    append(" is an invalid report.").toString());
        return rtdata.input.object;
    }
    
    protected abstract void select(PageBuilderContext context) throws Exception;
    
    public final void setViewerData(ReportViewerData data) {
        this.data = data;
    }

}
