package org.iocaste.texteditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditor;

public class Services extends AbstractFunction {
    private static final byte SEL_LINES1 = 0;
    private static final byte SEL_LINES2 = 1;
    private static final String[] QUERIES = {
            "select * from TXTEDITOR_LINE where TEXT_NAME = ?",
            "select * from TXTEDITOR_LINE where TEXT_NAME = ? and PAGE_NAME = ?"
    };

    public Services() {
        export("load", "load");
        export("save", "save");
    }
    
    public final Map<String, String> load(Message message) {
        ExtendedObject[] olines;
        StringBuilder page;
        boolean paragraph;
        Map<String, String> pages;
        String pageid, pageid_;
        String textname = message.get("textname");
        String pagename = message.get("pagename");
        Documents documents = new Documents(this);
        
        if (pagename == null)
            olines = documents.select(QUERIES[SEL_LINES1], textname);
        else
            olines = documents.select(QUERIES[SEL_LINES2], textname, pagename);
        
        if (olines == null)
            return null;
        
        pageid_ = "";
        page = null;
        pages = new HashMap<>();
        for (ExtendedObject oline : olines) {
            pageid = oline.getValue("PAGE_NAME");
            if (!pageid.equals(pageid_)) {
                if (pageid_.length() > 0)
                    pages.put(pageid, page.toString());
                
                page = new StringBuilder();
                pageid_ = pageid;
            }
            
            paragraph = oline.getValue("PARAGRAPH");
            if (page.length() > 0 && paragraph == true)
                page.append("\r\n");
                
            page.append(oline.getValue("LINE"));
        }
        
        if (pageid_.length() > 0)
            pages.put(pageid_, page.toString());
        
        return pages;
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
        object.setValue("PAGE_NAME", helper.pagename);
        object.setValue("TEXT_NAME", helper.textname);
        object.setValue("PARAGRAPH", helper.paragraph);
        object.setValue("LINE", line);
        
        return object;
    }
    
    public final void save(Message message) {
        long textid, pageid;
        int lines;
        LineHelper linehelper;
        String text;
        String[] textlines;
        ExtendedObject object;
        DocumentModel pagemodel;
        TextEditor editor = message.get("editor");
        Documents documents = new Documents(this);

        linehelper = new LineHelper();
        linehelper.textname = message.get("textname");
        object = new ExtendedObject(documents.getModel("TXTEDITOR_HEAD"));
        object.setValue("TEXT_NAME", linehelper.textname);
        textid = documents.getNextNumber("TXTED_TEXTID");
        object.setValue("TEXT_ID", textid);
        documents.save(object);
        
        pagemodel = documents.getModel("TXTEDITOR_PAGE");
        linehelper.textlinemodel = documents.getModel("TXTEDITOR_LINE");
        pageid = textid * 1000;
        for (String page : editor.getPages()) {
            pageid++;
            linehelper.pagename = page;
            object = new ExtendedObject(pagemodel);
            object.setValue("PAGE_ID", pageid);
            object.setValue("TEXT_NAME", linehelper.textname);
            object.setValue("PAGE_NAME", linehelper.pagename);
            documents.save(object);

            linehelper.i = pageid * 10000;
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
    public String textname, pagename, line;
    public long i;
    public int size;
    public DocumentModel textlinemodel;
}
