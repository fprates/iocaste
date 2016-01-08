package org.iocaste.report.common.export;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DownloadData;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.report.common.AbstractReportContext;

public class CSVGenerate extends AbstractActionHandler {
    private AbstractOutputExport export;
    
    public CSVGenerate(AbstractOutputExport export) {
        this.export = export;
    }
    
    private final byte[] compose(AbstractReportContext extcontext) {
        StringBuilder buffer;
        Map<String, ReportPrintItem> values;
        boolean head = false;
        Map<String, String> translations = export.getTranslations();
        
        buffer = new StringBuilder();
        values = new LinkedHashMap<>();
        export.setValues(values);
        for (ExtendedObject item : extcontext.items) {
            if (!head) {
                values.clear();
                export.printHeader(item.getModel());
                printline(translations, buffer, values);
                head = true;
            }
            values.clear();
            export.formatValues(item);
            printline(buffer, values);
        }
        buffer.append("\0");
        return buffer.toString().getBytes();
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String fd;
        Iocaste iocaste;
        byte[] content;
        String[] path;
        
        export.setContext(context);
        export.setOutputFile(context);
        path = export.getPath();
        context.downloaddata = new DownloadData();
        context.downloaddata.filename = path[path.length - 1].concat(".csv");
        context.downloaddata.fullname = path(".csv", path);
        context.downloaddata.contenttype = "text/csv";
        path = export.getPath();

        content = compose(getExtendedContext());
        iocaste = new Iocaste(context.function);
        path[path.length - 1] = context.downloaddata.filename;
        iocaste.delete(path);
        fd = iocaste.file(Iocaste.CREATE, path);
        iocaste.write(fd, content);
        iocaste.close(fd);
    }
    
    private final String path(String ext, String... path) {
        StringBuilder sb;
        
        sb = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).append("iocaste");
        for (String token : path)
            sb.append(File.separator).append(token);
        sb.append(ext);
        return sb.toString();
    }
    
    private final void printline(StringBuilder buffer,
            Map<String, ReportPrintItem> values) {
        printline(null, buffer, values);
    }
    
    private final void printline(Map<String, String> translations,
            StringBuilder buffer, Map<String, ReportPrintItem> values) {
        ReportPrintItem printitem;
        String value, translated;
        
        for (String key : values.keySet()) {
            printitem = values.get(key);
            value = printitem.getValue();
            if (value != null) {
                if ((translations != null) && translations.containsKey(value))
                    translated = translations.get(value);
                else
                    translated = value;
            } else {
                translated = "";
            }
            buffer.append(translated).append(";");
        }
        buffer.append("\n");
    }
}
