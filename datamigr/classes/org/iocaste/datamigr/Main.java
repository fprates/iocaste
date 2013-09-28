package org.iocaste.datamigr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

/**
 * Template par módulo interno do iocaste.
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    @SuppressWarnings("unchecked")
    public final void download() throws Exception {
        Map<String, Object> line;
        List<String> text;
        Object value;
        StringBuilder sb;
        InputComponent input = ((DataForm)context.view.getElement("entryform")).
                get("table");
        String table = input.get();
        Object[] lines = new Iocaste(this).select("select * from "+table);
        
        if (lines == null) {
            context.view.message(Const.ERROR, "no.records");
            return;
        }
        
        text = new ArrayList<>();
        for (Object object : lines) {
            line = (Map<String, Object>)object;
            
            sb = new StringBuilder();
            for (String key : line.keySet()) {
                if (sb.length() > 0)
                    sb.append(";");
                value = line.get(key);
                sb.append((value == null)? "" : value);
            }
            text.add(sb.toString());
        }
        
//        view.export("filename", table.concat(".txt"));
//        view.export("text", text);
        context.view.redirect("export");
    }
    
    public final void export(View view) {
        List<String> text = view.getParameter("text");
        String filename = view.getParameter("filename");
        
        for (String line : text)
            view.print(line.concat("\n"));
        
        view.setContentType("text/plain");
        view.setHeader("Content-Disposition",
                new StringBuilder("attachment; filename=\"").
                        append(filename).
                        append("\"").toString());
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    /**
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        InputComponent input;
        DataElement delement;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "entryform");
        pagecontrol.add("home");
        
        delement = new DataElement("TABLE_NAME");
        delement.setType(DataType.CHAR);
        delement.setLength(20);
        delement.setUpcase(true);
        
        input = new DataItem(form, Const.TEXT_FIELD, "table");
        input.setObligatory(true);
        input.setDataElement(delement);
        
        new Button(container, "download");
    }
}

class Context extends PageContext { }