package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.SearchHelpData;

public class ViewInstall extends AbstractInstallObject {
        
    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        ComplexModelInstall screenitem, cmodel;
        DataElement screenconfigitemid, screenconfigname, screenconfigvalue;
        DataElement screenconfigtype, screenactionname, screenactionclass;
        DataElement screenactionid, screenactiontype, screenspecitemname;
        DataElement screenitemtype, screenname, screenitemtext;
        DataElement screenactiontypetext, screenbool, screenlength;
        DocumentModelItem screen, speckey, spectypekey, actiontypekey;
        SearchHelpData shd;
        
        screenname = elementchar("WB_SCREEN_NAME", 16, false);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, false);
        screenitemtype = elementchar("WB_SCREEN_ITEM_TYPE", 24, false);
        screenitemtext = elementchar("WB_SCREEN_ITEM_TEXT", 40, false);
        screenconfigitemid = elementchar("WB_SCREEN_CFG_ID", 33, false);
        screenconfigname = elementchar("WB_SCREEN_CFG_NAME", 24, false);
        screenconfigvalue = elementchar("WB_SCREEN_CFG_VALUE", 255, false);
        screenconfigtype = elementnumc("WB_SCREEN_CFG_TYPE", 1);
        screenactionid = elementchar("WB_SCREEN_ACTION_ID", 19, false);
        screenactiontypetext = elementchar("WB_SCREEN_ACTION_TEXT", 10, false);
        screenactionname = elementchar("WB_SCREEN_ACTION_NAME", 40, false);
        screenactionclass = elementchar("WB_SCREEN_ACTION_CLASS", 255, false);
        screenactiontype = elementnumc("WB_SCREEN_ACTION_TYPE", 1);
        screenbool = elementbool("WB_SCREEN_BOOLEAN");
        screenlength = elementnumc("WB_SCREEN_LENGTH", 3);
        
        /*
         * project screens
         */
        model = tag("screen_head", modelInstance(
                "WB_SCREEN_HEADER", "WBSCRHD"));
        screen = model.key(
                "NAME", "SCRNM", screenname);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        
        /*
         * screen item types
         */
        model = modelInstance(
                "WB_SCREEN_SPEC_TYPE", "WBSCRSPECTYP");
        spectypekey = model.key(
                "TYPE", "SPCTY", screenitemtype);
        model.item(
                "TEXT", "SPCTX", screenitemtext);

        model.values("button", "Botão");
        model.values("dataform", "Formulário digitação");
        model.values("expandbar", "Área expansível");
        model.values("form", "Formulário HTML");
        model.values("fileupload", "Upload de arquivo");
        model.values("link", "Link html");
        model.values("listbox", "Campo de lista");
        model.values("nodelist", "Lista hierárquica");
        model.values("nodelistitem", "Item de lista hierárquica");
        model.values("navcontrol", "Controle de navegação");
        model.values("printarea", "Área de impressão");
        model.values("radiobutton", "Botão rádio");
        model.values("radiogroup", "Grupo de botão rádio");
        model.values("reporttool", "Ferramenta de relatório");
        model.values("standardcontainer", "Conteiner padrão");
        model.values("tabbedpane", "Conteiner de abas");
        model.values("tabbedpaneitem", "Aba");
        model.values("tabletool", "Ferramenta de tabela");
        model.values("text", "Texto de saída");
        model.values("texteditor", "Área de texto");
        model.values("textfield", "Campo de texto de extrada");
        model.values("tiles", "Tiles");
        model.values("parameter", "Parâmetros html");
        model.values("view", "Visão");
        
        shd = searchHelpInstance(
                "SH_WB_SCREEN_SPEC_TYPE", "WB_SCREEN_SPEC_TYPE");
        shd.setExport("TYPE");
        shd.add("TYPE");
        shd.add("TEXT");
        
        /*
         * project screen spec items
         */
        model = tag("screen_spec", modelInstance(
                "WB_SCREEN_SPEC", "WBSCRSPEC"));
        speckey = model.key(
                "NAME", "SITNM", screenspecitemname);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "SCREEN", "SCRID", screen);
        searchhelp(model.item(
                "PARENT", "SITPA", screenspecitemname), "SH_WB_SCREEN_SPEC");
        searchhelp(model.reference(
                "TYPE", "ITTYP", spectypekey), "SH_WB_SCREEN_SPEC_TYPE");
        
        shd = searchHelpInstance("SH_WB_SCREEN_SPEC", "WB_SCREEN_SPEC");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.add("SCREEN");
        
        /*
         * project screen config items
         */
        model = tag("screen_config", modelInstance(
                "WB_SCREEN_CONFIG", "WBSCRCFG"));
        model.key(
                "CONFIG_ID", "CFGID", screenconfigitemid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "SCREEN", "SCRID", screen);
        model.reference(
                "SPEC", "ITMID", speckey);
        model.item(
                "NAME", "CFGNM", screenconfigname);
        model.item(
                "VALUE", "CFGVL", screenconfigvalue);
        model.item(
                "TYPE", "CFGTY", screenconfigtype);
        
        /*
         * project screen tool items
         */
        model = tag("tool_item", modelInstance(
                "WB_SCR_TOOL_ITEM", "WBSCRTOOLIT"));
        model.key(
                "ITEM_ID", "ITMID", screenconfigitemid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "SCREEN", "SCRID", screen);
        model.reference(
                "SPEC", "SPCID", speckey);
        model.item(
                "NAME", "ITMNM", screenconfigname);
        model.item(
                "DISABLED", "DSBLD", screenbool);
        model.item(
                "INVISIBLE", "INVSB", screenbool);
        model.item(
                "VLENGTH", "VLNGT", screenlength);
        model.item(
                "LENGTH", "LNGTH", screenlength);
        model.item(
                "REQUIRED", "VLNGTH", screenbool);
        model.item(
                "FOCUS", "CFGTY", screenbool);
        
        /*
         * project screen action
         */
        model = modelInstance(
                "WB_SCR_ACTION_TYPE", "WBSCRACTTYP");
        actiontypekey = model.key(
                "TYPE", "ACTTY", screenactiontype);
        model.item(
                "TEXT", "ACTTX", screenactiontypetext);
        model.values(0, "Action");
        model.values(1, "Submit");
        model.values(2, "S/exibir");
        
        shd = searchHelpInstance("SH_WB_SCR_ACTION_TYPE", "WB_SCR_ACTION_TYPE");
        shd.setExport("TYPE");
        shd.add("TYPE");
        shd.add("TEXT");
        
        /*
         * project screen actions
         */
        model = tag("screen_action", modelInstance(
                "WB_SCREEN_ACTION", "WBSCRACT"));
        model.key(
                "ACTION_ID", "ACTID", screenactionid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "SCREEN", "SCRID", screen);
        model.item(
                "NAME", "ACTNM", screenactionname);
        searchhelp(model.item(
                "CLASS", "CLASS", screenactionclass), "SH_WB_JAVA_CLASS");
        searchhelp(model.reference(
                "TYPE", "ACTTY", actiontypekey), "SH_WB_SCR_ACTION_TYPE");
        
        screenitem = cmodelInstance("WB_SCREEN_ITEM");
        screenitem.header("screen_spec");
        screenitem.item("config", "screen_config").index = "NAME";
        screenitem.item("tool_item", "tool_item").index = "NAME";
        
        cmodel = tag("screens", cmodelInstance("WB_SCREENS"));
        cmodel.header("screen_head");
        cmodel.document("spec", screenitem);
        cmodel.item("action", "screen_action").keyformat = "%03d";
    }
    
}