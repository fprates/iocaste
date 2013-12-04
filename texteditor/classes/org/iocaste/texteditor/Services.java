package org.iocaste.texteditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditor;

public class Services extends AbstractFunction {

    public Services() {
        export("load", "load");
        export("register", "register");
        export("update", "update");
        export("unregister", "unregister");
    }
    
    public final Map<String, String> load(Message message) {
        ExtendedObject[] olines;
        StringBuilder page;
        boolean paragraph;
        Map<String, String> pages;
        String pageid, pageid_;
        Query query;
        String textname = message.get("textname");
        String pagename = message.get("pagename");
        Documents documents = new Documents(this);
        
        query = new Query();
        query.setModel("TXTEDITOR_LINE");
        query.andEqual("TEXT_NAME", textname);
        if (pagename != null)
            query.andEqual("PAGE_NAME", pagename);
        
        olines = documents.select(query);
        if (olines == null)
            return null;
        
        pageid_ = "";
        page = null;
        pages = new HashMap<>();
        for (ExtendedObject oline : olines) {
            pageid = oline.get("PAGE_NAME");
            if (!pageid.equals(pageid_)) {
                if (pageid_.length() > 0)
                    pages.put(pageid, page.toString());
                
                page = new StringBuilder();
                pageid_ = pageid;
            }
            
            paragraph = oline.get("PARAGRAPH");
            if (page.length() > 0 && paragraph == true)
                page.append("\r\n");
                
            page.append(oline.get("LINE"));
        }
        
        if (pageid_.length() > 0)
            pages.put(pageid_, page.toString());
        
        return pages;
    }
    
    public final void register(Message message) throws Exception {
        int lines;
        ExtendedObject object;
        Documents documents = new Documents(this);
        String name = message.get("textname");
        long id = documents.getNextNumber("TXTED_TEXTID");
        
        object = new ExtendedObject(documents.getModel("TXTEDITOR_HEAD"));
        object.set("TEXT_NAME", name);
        object.set("TEXT_ID", id);
        lines = documents.save(object);
        if (lines > 0)
            return;
        
        throw new IocasteException(new StringBuilder("Text ").
                append(name).append(" has already exists.").toString());
    }
    
    private final void saveDetails(LineHelper linehelper) {
        int lines;
        ExtendedObject object;
        String text;
        String[] textlines;
        long pageid = linehelper.textid * 1000;
        Documents documents = new Documents(this);
        DocumentModel pagemodel = documents.getModel("TXTEDITOR_PAGE");

        linehelper.textlinemodel = documents.getModel("TXTEDITOR_LINE");
        for (String page : linehelper.editor.getPages()) {
            pageid++;
            linehelper.pagename = page;
            object = new ExtendedObject(pagemodel);
            object.set("PAGE_ID", pageid);
            object.set("TEXT_NAME", linehelper.textname);
            object.set("PAGE_NAME", linehelper.pagename);
            documents.modify(object);

            linehelper.i = pageid * 10000;
            linehelper.size = linehelper.editor.getLineSize();
            text = linehelper.editor.getString(page);
            textlines = text.split("\r\n");
            for (String textline : textlines) {
                linehelper.paragraph = true;
                linehelper.line = textline;

                lines = textline.length() / linehelper.size;
                if (lines == 0) {
                    documents.modify(saveLine(linehelper, lines));
                    continue;
                }
                
                for (int l = 0; l < lines; l++) {
                    documents.modify(saveLine(linehelper, l));
                    linehelper.paragraph = false;
                }
            }
        }
    }
    
    private final ExtendedObject saveLine(LineHelper helper, int pos) {
        ExtendedObject object;
        String line;
        int linepos = pos * helper.size;
        
        if (helper.line.length() < helper.size)
            line = helper.line.substring(linepos);
        else
            line = helper.line.substring(linepos, linepos + helper.size);
        
        helper.i++;
        object = new ExtendedObject(helper.textlinemodel);
        object.set("LINE_ID", helper.i);
        object.set("PAGE_NAME", helper.pagename);
        object.set("TEXT_NAME", helper.textname);
        object.set("PARAGRAPH", helper.paragraph);
        object.set("LINE", line);
        
        return object;
    }
    
    public final void unregister(Message message) {
        String name = message.get("name");
        Query[] queries = new Query[3];
        
        queries[0] = new Query("delete");
        queries[0].setModel("TXTEDITOR_LINE");
        queries[0].andEqual("TEXT_NAME", name);
        queries[1] = new Query("delete");
        queries[1].setModel("TXTEDITOR_PAGE");
        queries[1].andEqual("TEXT_NAME", name);
        queries[2] = new Query("delete");
        queries[2].setModel("TXTEDITOR_HEAD");
        queries[2].andEqual("TEXT_NAME", name);
        new Documents(this).update(queries);
    }
    
    public final void update(Message message) throws Exception {
        ExtendedObject[] objects;
        Query[] queries;
        LineHelper linehelper;
        Documents documents = new Documents(this);

        linehelper = new LineHelper();
        linehelper.textname = message.get("textname");
        linehelper.editor = message.get("editor");
        
        queries = new Query[2];
        queries[0] = new Query();
        queries[0].setModel("TXTEDITOR_HEAD");
        queries[0].setMaxResults(1);
        queries[0].andEqual("TEXT_NAME", linehelper.textname);
        objects = documents.select(queries[0]);
        if (objects == null)
            throw new IocasteException(new StringBuilder("Text ").
                    append(linehelper.textname).
                    append(" doesn't exist.").toString());
        
        linehelper.textid = objects[0].getl("TEXT_ID");
        
        queries[0] = new Query("delete");
        queries[0].setModel("TXTEDITOR_LINE");
        queries[0].andEqual("TEXT_NAME", linehelper.textname);
        queries[1] = new Query("delete");
        queries[1].setModel("TXTEDITOR_PAGE");
        queries[1].andEqual("TEXT_NAME", linehelper.textname);
        documents.update(queries);
        
        saveDetails(linehelper);
    }
}

class LineHelper {
    public boolean paragraph;
    public String textname, pagename, line;
    public long i, textid;
    public int size;
    public DocumentModel textlinemodel;
    public TextEditor editor;
}
