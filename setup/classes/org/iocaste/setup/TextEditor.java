package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class TextEditor {

    public static final void install(InstallData data) {
        DataElement element;
        DocumentModel model;
        DocumentModelItem item, textid, pageid;
        
        /*
         * cabeçalho
         */
        model = data.getModel("TXTEDITOR_HEAD", "TXTED_HEAD", null);
        element = new DataElement("TXTED_ID");
        element.setType(DataType.CHAR);
        element.setLength(24);
        element.setUpcase(true);
        textid = new DocumentModelItem("TEXT_ID");
        textid.setTableFieldName("TXTID");
        textid.setDataElement(element);
        model.add(textid);
        model.add(new DocumentModelKey(textid));
        
        /*
         * páginas
         */
        model = data.getModel("TXTEDITOR_PAGE", "TXTED_PAGE", null);
        element = new DataElement("TXTED_PAGE");
        element.setType(DataType.NUMC);
        element.setLength(9);
        pageid = new DocumentModelItem("PAGE_ID");
        pageid.setTableFieldName("PAGID");
        pageid.setDataElement(element);
        model.add(pageid);
        model.add(new DocumentModelKey(pageid));
        
        item = new DocumentModelItem("TEXT_ID");
        item.setTableFieldName("TXTID");
        item.setDataElement(textid.getDataElement());
        item.setReference(textid);
        model.add(item);
        
        element = new DataElement("TXTED_PAGE_NAME");
        element.setType(DataType.CHAR);
        element.setLength(128);
        element.setUpcase(false);
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("PAGNM");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * itens
         */
        model = data.getModel("TXTEDITOR_LINE", "TXTED_LINE", null);
        element = new DataElement("TXTED_LINE_ID");
        element.setType(DataType.NUMC);
        element.setLength(15);
        item = new DocumentModelItem("LINE_ID");
        item.setTableFieldName("LINID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem("PAGE_ID");
        item.setTableFieldName("PAGID");
        item.setDataElement(pageid.getDataElement());
        item.setReference(pageid);
        model.add(item);
        
        item = new DocumentModelItem("TEXT_ID");
        item.setTableFieldName("TXTID");
        item.setDataElement(textid.getDataElement());
        item.setReference(textid);
        model.add(item);
        
        element = new DataElement("TXTED_LINE");
        element.setType(DataType.CHAR);
        element.setLength(255);
        element.setUpcase(false);
        item = new DocumentModelItem("LINE");
        item.setTableFieldName("TXTLN");
        item.setDataElement(element);
        model.add(item);
        
        data.addNumberFactory("TXTED_PAGEID");
    }
}
