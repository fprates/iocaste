package org.iocaste.workbench;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;

public class ProjectModels {
    public static final void install(InstallData data) {
        DocumentModel model;
        DataElement element;
        DocumentModelItem item, projectname, packagename, sourceid;
        SearchHelpData shd;
        
        /*
         * Cabeçalho do projeto iocaste
         */
        model = data.getModel("ICSTPRJ_HEADER", "IPHEADER", null);
        
        // nome do projeto
        element = new DummyElement("PACKAGE.NAME");
        
        projectname = new DocumentModelItem("NAME");
        projectname.setTableFieldName("PRJNM");
        projectname.setDataElement(element);
        
        model.add(projectname);
        model.add(new DocumentModelKey(projectname));
        
        shd = new SearchHelpData("SH_IOCASTE_PROJECT");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.setModel("IOCASTE_PROJECT_HEADER");
        data.add(shd);
        
        /*
         * Visões do projeto
         */
        model = data.getModel("ICSTPRJ_VIEWS", "IPVIEWS", null);
        
        // identificador da visão
        element = new DataElement("ICSTPRJ_VIEWS.IDENT");
        element.setType(DataType.CHAR);
        element.setLength(69);
        element.setUpcase(false);
        
        item = new DocumentModelItem("IDENT");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // projeto da visão
        item = new DocumentModelItem("PROJECT");
        item.setTableFieldName("PRJNM");
        item.setDataElement(projectname.getDataElement());
        item.setReference(projectname);
        
        model.add(item);
        
        // nome da visão
        element = new DataElement("ICSTPRJ_VIEWS.NAME");
        element.setType(DataType.CHAR);
        element.setLength(30);
        element.setUpcase(false);
        
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("PJVNM");
        item.setDataElement(element);
        
        model.add(item);
        
        /*
         * Pacotes
         */
        model = data.getModel("ICSTPRJ_PACKAGES", "IPPROJECTS", null);
        
        // nome do pacote
        element = new DataElement("ICSTPRJ_PACKAGES.NAME");
        element.setType(DataType.CHAR);
        element.setLength(60);
        element.setUpcase(false);
        
        packagename = new DocumentModelItem("NAME");
        packagename.setTableFieldName("PKGNM");
        packagename.setDataElement(element);
        
        model.add(packagename);
        model.add(new DocumentModelKey(packagename));
        
        // projeto referência
        item = new DocumentModelItem("PROJECT");
        item.setTableFieldName("PRJNM");
        item.setDataElement(projectname.getDataElement());
        item.setReference(projectname);
        
        model.add(item);
        
        /*
         * fontes
         */
        model = data.getModel("ICSTPRJ_SOURCES", "IPSOURCES", null);
        
        // identificador
        element = new DataElement("ICSTPRJ_SOURCES.IDENT");
        element.setType(DataType.CHAR);
        element.setLength(69);
        element.setUpcase(false);
        
        sourceid = new DocumentModelItem("IDENT");
        sourceid.setTableFieldName("IDENT");
        sourceid.setDataElement(element);
        
        model.add(sourceid);
        model.add(new DocumentModelKey(sourceid));
        
        // pacote referência
        item = new DocumentModelItem("PACKAGE");
        item.setTableFieldName("PKGNM");
        item.setDataElement(packagename.getDataElement());
        item.setReference(packagename);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // nome
        element = new DataElement("ICSTPRJ_SOURCES.NAME");
        element.setType(DataType.CHAR);
        element.setLength(60);
        element.setUpcase(false);
        
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("SRCNM");
        item.setDataElement(element);
        
        model.add(item);
        
        /*
         * texto fonte
         */
        model = data.getModel("ICSTPRJ_SRCCODE", "IPSRCCODE", null);
        
        // identificador
        element = new DataElement("ICSTPRJ_SRCCODE.IDENT");
        element.setType(DataType.CHAR);
        element.setLength(69);
        element.setUpcase(false);
        
        item = new DocumentModelItem("IDENT");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // fonte
        item = new DocumentModelItem("SOURCE");
        item.setTableFieldName("SRCID");
        item.setReference(sourceid);
        item.setDataElement(sourceid.getDataElement());
        
        model.add(item);
        
        // pacote
        item = new DocumentModelItem("PACKAGE");
        item.setTableFieldName("PKGNM");
        item.setDataElement(packagename.getDataElement());
        item.setReference(packagename);
        
        model.add(item);
        
        // parágrafo
        element = new DataElement("ICSTPRJ_SRCCODE.PARAGRAPH");
        element.setType(DataType.BOOLEAN);
        
        item = new DocumentModelItem("PARAGRAPH");
        item.setTableFieldName("PGRPH");
        item.setDataElement(element);
        
        model.add(item);
        
        // line
        element = new DataElement("ICSTPRJ_SRCCODE.LINE");
        element.setType(DataType.CHAR);
        element.setLength(80);
        element.setUpcase(false);
        
        item = new DocumentModelItem("LINE");
        item.setTableFieldName("SLINE");
        item.setDataElement(element);
        
        model.add(item);
    }

}
