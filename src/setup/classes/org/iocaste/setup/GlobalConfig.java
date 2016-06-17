package org.iocaste.setup;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.SearchHelpData;

public class GlobalConfig extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        SearchHelpData shd;
        DocumentModelItem nmcfg, idcfgit;
        DataElement packagename, gconfigitemid, gconfigitemnm, gconfigitemtp;
        DataElement gconfigitemvl;
        ModelInstall model;

        // identificador
        packagename = new DummyElement("PACKAGE.NAME");
        gconfigitemid = elementnumc("GLOBAL_CONFIG_ITEM.ID", 8);
        gconfigitemnm = elementchar("GLOBAL_CONFIG_ITEM.NAME", 64, true);
        gconfigitemtp = elementnumc("GLOBAL_CONFIG_ITEM.TYPE", 2);
        gconfigitemvl = elementchar("GLOBAL_CONFIG_VALUES.VALUE", 256, false);
        /*
         * Cabeçalho de configuração global
         */
        model = modelInstance("GLOBAL_CONFIG", "GCNFGHDR");
        nmcfg = searchhelp(model.key(
                "NAME", "NMCFG", packagename), "SH_PKG_CONFIG");
        model.item(
                "CURRENT", "CRRID", gconfigitemid);
        
        context.getInstallData().addNumberFactory("GLOBALCFG");
        
        /*
         * Itens da configuração global
         */
        model = modelInstance("GLOBAL_CONFIG_ITEM", "GCNFGITM");
        idcfgit = model.key(
                "ID", "IDENT", gconfigitemid);
        model.reference(
                "GLOBAL_CONFIG", "IDCFG", nmcfg);
        model.item(
                "NAME", "PNAME", gconfigitemnm);
        model.item(
                "TYPE", "DTYPE", gconfigitemtp);
        
        /*
         * Valores da configuração global
         */
        model = modelInstance("GLOBAL_CONFIG_VALUES", "GCNFGVAL");
        model.key(
                "ID", "IDENT", idcfgit);
        model.reference(
                "GLOBAL_CONFIG", "IDCFG", nmcfg);
        model.item(
                "VALUE", "VLCFG", gconfigitemvl);
        
        shd = searchHelpInstance("SH_PKG_CONFIG", "GLOBAL_CONFIG");
        shd.setExport("NAME");
        shd.add("NAME");
    }
}
