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
        export("update_text", "updateText");
        export("unregister", "unregister");
    }
    
    public final Map<Long, String> load(Message message) {
        ExtendedObject[] olines, opages;
        StringBuilder page;
        boolean paragraph;
        Map<Long, String> pages;
        long pagenr_;
        Query query;
        String textname = message.get("textname");
        long pagenr = message.getl("pagenr");
        Documents documents = new Documents(this);
        
        query = new Query();
        query.setModel("TXTEDITOR_PAGE");
        query.andEqual("TEXT_NAME", textname);
        if (pagenr > 0)
            query.andEqual("PAGE_NR", pagenr);
        opages = documents.select(query);
        if (opages == null)
            return null;
        
        query = new Query();
        query.setModel("TXTEDITOR_LINE");
        query.andEqual("TEXT_NAME", textname);
        if (pagenr > 0)
            query.andEqual("PAGE_NR", pagenr);
        olines = documents.select(query);
        if (olines == null)
            return null;
        
        pages = new HashMap<>();
        for (ExtendedObject opage : opages) {
            pagenr = opage.getl("PAGE_NR");
            page = new StringBuilder();
            for (ExtendedObject oline : olines) {
                pagenr_ = oline.getl("PAGE_NR");
                if (pagenr != pagenr_)
                    continue;
                
                paragraph = oline.get("PARAGRAPH");
                if (page.length() > 0 && paragraph == true)
                    page.append("\r\n");
                    
                page.append(oline.get("LINE"));
            }
            pages.put(pagenr, page.toString());
        }
        
        return pages;
    }
    
    public final void register(Message message) throws Exception {
        int lines;
        ExtendedObject object;
        Documents documents = new Documents(this);
        String name = message.get("name");
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
        long pageid = linehelper.textid * 1000000;
        Documents documents = new Documents(this);
        DocumentModel pagemodel = documents.getModel("TXTEDITOR_PAGE");

        linehelper.textlinemodel = documents.getModel("TXTEDITOR_LINE");
        for (long page : linehelper.pages.keySet()) {
            pageid += page;
            linehelper.pagenr = page;
            object = new ExtendedObject(pagemodel);
            object.set("PAGE_ID", pageid);
            object.set("TEXT_NAME", linehelper.textname);
            object.set("PAGE_NR", linehelper.pagenr);
            documents.modify(object);

            linehelper.i = pageid * 10000;
            linehelper.size = linehelper.linesize;
            text = linehelper.pages.get(page);
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
        object.set("PAGE_NR", helper.pagenr);
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
        String textname = message.get("textname");
        TextEditor editor = message.get("editor");
        Map<Long, String> pages = new HashMap<>();
        
        for (long page : editor.getPages())
            pages.put(page, editor.getString(page));
        
        update(textname, pages, editor.getWidth());
    }
    
    private final void update(String textobj, Map<Long, String> pages,
            int linesize) throws Exception {
        ExtendedObject[] objects;
        Query[] queries;
        LineHelper linehelper;
        Documents documents = new Documents(this);
        
        linehelper = new LineHelper();
        linehelper.textname = textobj;
        
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
        
        for (long pagenr : pages.keySet()) {
            queries[0] = new Query("delete");
            queries[0].setModel("TXTEDITOR_LINE");
            queries[0].andEqual("TEXT_NAME", linehelper.textname);
            queries[0].andEqual("PAGE_NR", pagenr);
            
            queries[1] = new Query("delete");
            queries[1].setModel("TXTEDITOR_PAGE");
            queries[1].andEqual("TEXT_NAME", linehelper.textname);
            queries[1].andEqual("PAGE_NR", pagenr);
            documents.update(queries);
        }
        
        linehelper.textid = objects[0].getl("TEXT_ID");
        linehelper.pages = pages;
        linehelper.linesize = linesize;
        saveDetails(linehelper);
    }
    
    public final void updateText(Message message) throws Exception {
        String textobj = message.get("textobj");
        long page = message.get("page");
        String text = message.get("text");
        int linesize = message.get("line_size");
        Map<Long, String> pages = new HashMap<>();
        
        pages.put(page, text);
        update(textobj, pages, linesize);
    }
}

class LineHelper {
    public boolean paragraph;
    public String textname, line;
    public long i, textid, pagenr;
    public int size, linesize;
    public DocumentModel textlinemodel;
    public Map<Long, String> pages;
}
