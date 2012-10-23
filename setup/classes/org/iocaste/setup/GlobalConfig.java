package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.InstallData;

public class GlobalConfig {
    
    /**
     * 
     * @param data
     */
    public final static void install(InstallData data) {
        DocumentModelItem item, reference, nmcfg, idcfgit;
        DataElement element;
        DocumentModel model;
        
        /*
         * Cabeçalho de configuração global
         */
        model = data.getModel("GLOBAL_CONFIG", "GCNFGHDR", null);
        
        // identificador
        reference = new DummyModelItem("PACKAGE", "NAME");
        element = new DummyElement("PACKAGE.NAME");
        
        nmcfg = new DocumentModelItem();
        nmcfg.setName("NAME");
        nmcfg.setTableFieldName("NMCFG");
        nmcfg.setDataElement(element);
        nmcfg.setReference(reference);
        model.add(nmcfg);
        model.add(new DocumentModelKey(nmcfg));
        
        // id do último parâmetro
        element = new DataElement();
        element.setName("GLOBAL_CONFIG_ITEM.ID");
        element.setType(DataType.NUMC);
        element.setLength(8);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("CURRENT");
        item.setTableFieldName("CRRID");
        item.setDataElement(element);
        model.add(item);
        
        data.addNumberFactory("GLOBALCFG");
        
        /*
         * Itens da configuração global
         */
        model = data.getModel("GLOBAL_CONFIG_ITEM", "GCNFGITM", null);
        
        // identificador
        idcfgit = new DocumentModelItem();
        idcfgit.setName("ID");
        idcfgit.setTableFieldName("IDENT");
        idcfgit.setDataElement(element);
        model.add(idcfgit);
        model.add(new DocumentModelKey(idcfgit));
        
        // cabeçalho
        item = new DocumentModelItem();
        item.setName("GLOBAL_CONFIG");
        item.setTableFieldName("IDCFG");
        item.setReference(nmcfg);
        item.setDataElement(nmcfg.getDataElement());
        model.add(item);
        
        // nome do parâmetro
        element = new DataElement();
        element.setName("GLOBAL_CONFIG_ITEM.NAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setTableFieldName("PNAME");
        item.setDataElement(element);
        model.add(item);
        
        // tipo de dado
        element = new DataElement();
        element.setName("GLOBAL_CONFIG_ITEM.TYPE");
        element.setType(DataType.NUMC);
        element.setLength(2);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("TYPE");
        item.setTableFieldName("DTYPE");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * Valores da configuração global
         */
        model = data.getModel("GLOBAL_CONFIG_VALUES", "GCNFGVAL", null);
        
        // identificador
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setReference(idcfgit);
        item.setDataElement(idcfgit.getDataElement());
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // valor
        element = new DataElement();
        element.setName("GLOBAL_CONFIG_VALUES.VALUE");
        element.setType(DataType.CHAR);
        element.setLength(256);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("VALUE");
        item.setTableFieldName("VLCFG");
        item.setDataElement(element);
        model.add(item);
    }
}
