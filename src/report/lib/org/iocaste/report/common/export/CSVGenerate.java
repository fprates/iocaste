package org.iocaste.report.common.export;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DownloadData;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.report.common.AbstractReportContext;

public class CSVGenerate extends AbstractActionHandler {
    private Map<String, AbstractOutputExport> export;
    private PageBuilderContext context;
    
    public CSVGenerate(Map<String, AbstractOutputExport> export) {
        this.export = export;
    }
    
    private final byte[] compose(AbstractReportContext extcontext) {
        StringBuilder buffer;
        Map<String, ReportPrintItem> values;
        boolean head = false;
        
        buffer = new StringBuilder("\ufeff");
        values = new LinkedHashMap<>();
        extcontext.export.setValues(values);
        for (ExtendedObject item : extcontext.items) {
            if (!head) {
                values.clear();
                extcontext.export.printHeader(item.getModel());
                printline(buffer, values);
                head = true;
            }
            values.clear();
            extcontext.export.formatValues(item);
            printline(buffer, values);
        }
        buffer.append("\0");
        return buffer.toString().getBytes();
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String fd;
        Iocaste iocaste;
        String[] path;
        AbstractReportContext extcontext;
        
        this.context = context;
        extcontext = getExtendedContext();
        extcontext.export = this.export.get(context.action);
        extcontext.export.setContext(context);
        extcontext.export.setOutputFile(context);
        path = extcontext.export.getPath();
        context.downloaddata = new DownloadData();
        context.downloaddata.filename = path[path.length - 1].concat(".csv");
        context.downloaddata.contenttype = "text/csv";
        context.downloaddata.contentencoding = extcontext.export.getEncoding();
        path = extcontext.export.getPath();

        context.downloaddata.content = compose(extcontext);
        iocaste = new Iocaste(context.function);
        path[path.length - 1] = context.downloaddata.filename;
        iocaste.delete(path);
        fd = iocaste.file(Iocaste.CREATE, path);
        iocaste.write(fd, context.downloaddata.content);
        iocaste.close(fd);
    }
    
    private final void printline(StringBuilder buffer,
            Map<String, ReportPrintItem> values) {
        ReportPrintItem printitem;
        String value, translated;
        
        for (String key : values.keySet()) {
            printitem = values.get(key);
            value = printitem.getValue();
            if (value != null) {
                translated = context.messages.get(value);
                if (translated == null)
                    translated = value;
            } else {
                translated = "";
            }
            buffer.append(translated).append(";");
        }
        buffer.append("\n");
    }
}
