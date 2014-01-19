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
        DocumentModelItem item, textnm, pagenr;
        
        /*
         * cabeçalho
         */
        model = data.getModel("TXTEDITOR_HEAD", "TXTED_HEAD", null);
        element = new DataElement("TXTED_TEXTNAME");
        element.setType(DataType.CHAR);
        element.setLength(24);
        element.setUpcase(true);
        textnm = new DocumentModelItem("TEXT_NAME");
        textnm.setTableFieldName("TXTNM");
        textnm.setDataElement(element);
        model.add(textnm);
        model.add(new DocumentModelKey(textnm));
        
        element = new DataElement("TXTED_TEXT_ID");
        element.setType(DataType.NUMC);
        element.setLength(6);
        item = new DocumentModelItem("TEXT_ID");
        item.setTableFieldName("TXTID");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * páginas
         */
        model = data.getModel("TXTEDITOR_PAGE", "TXTED_PAGE", null);
        element = new DataElement("TXTED_PAGE_ID");
        element.setType(DataType.NUMC);
        element.setLength(12);
        item = new DocumentModelItem("PAGE_ID");
        item.setTableFieldName("PAGID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        item = new DocumentModelItem("TEXT_NAME");
        item.setTableFieldName("TXTNM");
        item.setDataElement(textnm.getDataElement());
        item.setReference(textnm);
        model.add(item);
        
        element = new DataElement("TXTED_PAGE_NR");
        element.setType(DataType.NUMC);
        element.setLength(6);
        pagenr = new DocumentModelItem("PAGE_NR");
        pagenr.setTableFieldName("PAGNR");
        pagenr.setDataElement(element);
        model.add(pagenr);
        
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
        
        item = new DocumentModelItem("TEXT_NAME");
        item.setTableFieldName("TXTNM");
        item.setDataElement(textnm.getDataElement());
        item.setReference(textnm);
        model.add(item);
        
        item = new DocumentModelItem("PAGE_NR");
        item.setTableFieldName("PAGNR");
        item.setDataElement(pagenr.getDataElement());
        model.add(item);
        
        element = new DataElement("TXTED_PARAGRAPH");
        element.setType(DataType.BOOLEAN);
        item = new DocumentModelItem("PARAGRAPH");
        item.setTableFieldName("PARAG");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("TXTED_LINE");
        element.setType(DataType.CHAR);
        element.setLength(255);
        element.setUpcase(false);
        item = new DocumentModelItem("LINE");
        item.setTableFieldName("TXTLN");
        item.setDataElement(element);
        model.add(item);
        
        data.addNumberFactory("TXTED_TEXTID");
    }
}
