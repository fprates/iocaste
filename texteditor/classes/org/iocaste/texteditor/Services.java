package org.iocaste.texteditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditor;

public class Services extends AbstractFunction {

    public Services() {
        export("save", "save");
    }
    
    private final ExtendedObject registerLine(LineHelper helper, int pos) {
        ExtendedObject object;
        String line;
        int linepos = pos * helper.size;
        
        if (helper.line.length() < helper.size)
            line = helper.line.substring(linepos);
        else
            line = helper.line.substring(linepos, linepos + helper.size);
        
        helper.i++;
        object = new ExtendedObject(helper.textlinemodel);
        object.setValue("LINE_ID", helper.i);
        object.setValue("PAGE_ID", helper.pageid);
        object.setValue("TEXT_ID", helper.textid);
        object.setValue("LINE", line);
        
        return object;
    }
    
    public final void save(Message message) {
        int lines;
        LineHelper linehelper;
        String text;
        String[] textlines;
        ExtendedObject object;
        DocumentModel pagemodel;
        TextEditor editor = message.get("editor");
        Documents documents = new Documents(this);

        linehelper = new LineHelper();
        linehelper.textid = message.get("id");
        object = new ExtendedObject(documents.getModel("TXTEDITOR_HEAD"));
        object.setValue("TEXT_ID", linehelper.textid);
        documents.save(object);
        
        pagemodel = documents.getModel("TXTEDITOR_PAGE");
        linehelper.textlinemodel = documents.getModel("TXTEDITOR_LINE");
        for (String page : editor.getPages()) {
             linehelper.pageid = documents.getNextNumber("TXTED_PAGEID");
            
            object = new ExtendedObject(pagemodel);
            object.setValue("PAGE_ID", linehelper.pageid);
            object.setValue("TEXT_ID", linehelper.textid);
            object.setValue("NAME", page);
            documents.save(object);

            linehelper.i = linehelper.pageid * 10000;
            linehelper.size = editor.getLineSize();
            text = editor.getString(page);
            textlines = text.split("\r\n");
            for (String textline : textlines) {
                linehelper.paragraph = true;
                linehelper.line = textline;

                lines = textline.length() / linehelper.size;
                if (lines == 0) {
                    documents.save(registerLine(linehelper, lines));
                    continue;
                }
                
                for (int l = 0; l < lines; l++) {
                    documents.save(registerLine(linehelper, l));
                    linehelper.paragraph = false;
                }
            }
        }
    }
}

class LineHelper {
    public boolean paragraph;
    public String textid, line;
    public long i, pageid;
    public int size;
    public DocumentModel textlinemodel;
}
