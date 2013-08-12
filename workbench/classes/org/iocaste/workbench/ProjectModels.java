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
        DocumentModelItem projectid, packageid;
        SearchHelpData shd;
        
        /*
         * Cabeçalho do projeto iocaste
         */
        model = data.getModel("ICSTPRJ_HEADER", "IPHEADER", null);
        
        // identificador do projeto
        element = new DataElement("PROJECT.ID");
        element.setType(DataType.NUMC);
        element.setLength(6);
        projectid = new DocumentModelItem("ID");
        projectid.setTableFieldName("IDENT");
        projectid.setDataElement(element);
        model.add(projectid);
        model.add(new DocumentModelKey(projectid));
        
        data.addNumberFactory("IP_PRJID");
        
        /*
         * Nomes de projeto
         */
        model = data.getModel("ICSTPRJ_PROJECT_NAMES", "IPPRJNAMES", null);
        
        // nome do projeto
        element = new DummyElement("PACKAGE.NAME");
        projectname = new DocumentModelItem("NAME");
        projectname.setTableFieldName("PRJNM");
        projectname.setDataElement(element);
        model.add(projectname);
        model.add(new DocumentModelKey(projectname));
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(projectid.getDataElement());
        item.setReference(projectid);
        model.add(item);
        
        shd = new SearchHelpData("SH_IOCASTE_PROJECT");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.setModel("ICSTPRJ_PROJECT_NAMES");
        data.add(shd);
        
        /*
         * Pacotes
         */
        model = data.getModel("ICSTPRJ_PACKAGES", "IPPROJECTS", null);
        
        // identificador do pacote
        element = new DataElement("ICSTPRJ_PACKAGES.ID");
        element.setType(DataType.NUMC);
        element.setLength(9);
        packageid = new DocumentModelItem("ID");
        packageid.setTableFieldName("IDENT");
        packageid.setDataElement(element);
        model.add(packageid);
        model.add(new DocumentModelKey(packageid));
        
        // projeto referência
        item = new DocumentModelItem("PROJECT");
        item.setTableFieldName("PRJID");
        item.setDataElement(projectid.getDataElement());
        model.add(item);
        
        // nome do pacote
        element = new DataElement("ICSTPRJ_PACKAGES.NAME");
        element.setType(DataType.CHAR);
        element.setLength(128);
        element.setUpcase(false);
        packagename = new DocumentModelItem("NAME");
        packagename.setTableFieldName("PKGNM");
        packagename.setDataElement(element);
        model.add(packagename);
        
        /*
         * fontes
         */
        model = data.getModel("ICSTPRJ_SOURCES", "IPSOURCES", null);
        
        // identificador
        element = new DataElement("ICSTPRJ_SOURCES.ID");
        element.setType(DataType.NUMC);
        element.setLength(12);
        sourceid = new DocumentModelItem("ID");
        sourceid.setTableFieldName("IDENT");
        sourceid.setDataElement(element);
        model.add(sourceid);
        model.add(new DocumentModelKey(sourceid));
        
        // pacote referência
        item = new DocumentModelItem("PACKAGE");
        item.setTableFieldName("PKGID");
        item.setDataElement(packageid.getDataElement());
        item.setReference(packageid);
        model.add(item);
        
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
        element = new DataElement("ICSTPRJ_SRCCODE.ID");
        element.setType(DataType.NUMC);
        element.setLength(16);
        item = new DocumentModelItem("ID");
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
        item.setTableFieldName("PKGID");
        item.setDataElement(packageid.getDataElement());
        item.setReference(packageid);
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
