package org.iocaste.datadict;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Common {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    
    public static final byte MODELNAME = 0;
    public static final byte MODELCLASS = 1;
    public static final byte MODELTABLE = 2;
    
    public static final byte TABLE = 0;
    public static final byte SH = 1;
    
    public enum ItensNames {
        NAME("item.name", "DATAELEMENT.NAME"),
        TABLE_FIELD("item.tablefield", "MODELITEM.FIELDNAME"),
        CLASS_FIELD("item.classfield", "MODELITEM.ATTRIB"),
        KEY("item.key", null), 
        TYPE("item.type", null),
        LENGTH("item.length", "DATAELEMENT.LENGTH"),
        UPCASE("item.upcase", null),
        ITEM_REFERENCE("item.reference", "MODEL.NAME"),
        MODEL_REFERENCE("model.reference", "MODELITEM.NAME"),
        SEARCH_HELP("item.sh", "SEARCH_HELP.NAME");
        
        private String name, de;
        
        ItensNames(String name, String de) {
            this.name = name;
            this.de = de;
        }
        
        public final String getDataElement() {
            return de;
        }
        
        public final String getName() {
            return name;
        }
    };
    
    /**
     * 
     * @param function
     * @return
     * @throws Exception
     */
    public static final Map<ItensNames, DataElement> getFieldReferences(
            Function function) throws Exception {
        Map<ItensNames, DataElement> references =
                new HashMap<ItensNames, DataElement>();
        Documents docs = new Documents(function);
        
        for (ItensNames itemname : ItensNames.values())
            references.put(itemname,
                    docs.getDataElement(itemname.getDataElement()));
        
        return references;
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    public static final byte getMode(ViewData vdata) {
        return vdata.getParameter("mode");
    }
    
    /**
     * 
     * @param modo
     * @param item
     * @param name
     * @return
     */
    public static final String getTableValue(byte modo, TableItem item,
            String name) {
        return ((InputComponent)item.get(name)).getValue();
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public static final int getTpObjectValue(ViewData view) {
        RadioButton tpobj = view.getElement("tpobject");
        
        return Integer.parseInt(tpobj.getValue());
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    public static final boolean hasItemDuplicated(ViewData vdata) {
        String name, classfield, tablefield;
        String testname, testclassfield, testtablefield;
        Table itens = vdata.getElement("itens");
        byte modo = Common.getMode(vdata);
        
        for (TableItem item : itens.getItens()) {
            name = getTableValue(modo, item, "item.name");
            classfield = getTableValue(modo, item, "item.classfield");
            tablefield = getTableValue(modo, item, "item.tablefield");
            
            for (TableItem test : itens.getItens()) {
                if (item == test)
                    continue;
                
                testname = getTableValue(modo, test, "item.name");
                testclassfield = getTableValue(modo, test, "item.classfield");
                testtablefield = getTableValue(modo, test, "item.tablefield");
                
                if (name.equals(testname)) {
                    vdata.message(Const.ERROR, "item.name.duplicated");
                    return true;
                }

                if (classfield != null && classfield.equals(testclassfield)) {
                    vdata.message(Const.ERROR, "item.classfield.duplicated");
                    return true;
                }
                
                if (tablefield != null && tablefield.equals(testtablefield)) {
                    vdata.message(Const.ERROR, "item.tablefield.duplicated");
                    return true;
                }
                    
            }
        }
        
        return false;
    }
    
    /**
     * 
     * @param itens
     * @param mode
     * @param modelitem
     */
    public static final void insertItem(ItemConfig config) {
        ListBox list;
        DocumentModel model;
        DataElement dataelement = null;
        DocumentModelItem itemref = null;
        FieldHelper helper = new FieldHelper();
        DocumentModelItem modelitem = config.getModelItem();
        Map<ItensNames, DataElement> references = config.getReferences();
        ViewData view = config.getView();
        
        if (modelitem != null) {
            dataelement = modelitem.getDataElement();
            itemref = modelitem.getReference();
        }
        
        helper.item = new TableItem(config.getTable());
        helper.mode = config.getMode();
        
        for (ItensNames itemname : ItensNames.values()) {
            helper.name = itemname.getName();
            helper.reference = references.get(itemname);
            
            if (helper.name.equals("item.tablefield")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null : modelitem.getTableFieldName();
                helper.obligatory = true;
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.classfield")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null : modelitem.getAttributeName();
                helper.obligatory = false;
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.name")) {
                helper.reference.setLength(24);                
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?null:modelitem.getName();
                helper.obligatory = true;

                if (view != null)
                    view.setFocus(newField(helper));
                else
                    newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.length")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?null:Integer.toString(
                        dataelement.getLength());
                helper.obligatory = true;

                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.key")) {
                helper.type = Const.CHECKBOX;
                helper.obligatory = false;
                
                if (modelitem != null) {
                    model = modelitem.getDocumentModel();
                    helper.value =  model.isKey(modelitem)? "on" : "off";
                } else {
                    helper.value = "off";
                }
                
                newField(helper);
                
                continue;
            }
        
            if (helper.name.equals("item.type")) {
                helper.obligatory = false;
                helper.type = Const.LIST_BOX;
                helper.value = (modelitem == null)?null : Integer.toString(
                        dataelement.getType());
                
                list = (ListBox)newField(helper);
                list.add("char", Integer.toString(DataType.CHAR));
                list.add("numc", Integer.toString(DataType.NUMC));
                
                continue;
            }
            
            if (helper.name.equals("item.upcase")) {
                helper.type = Const.CHECKBOX;
                helper.obligatory = false;
                
                if (modelitem != null) {
                    model = modelitem.getDocumentModel();
                    helper.value =  dataelement.isUpcase()? "on" : "off";
                } else {
                    helper.value = "off";
                }
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.reference")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (itemref == null)? null : itemref.getName();
                helper.obligatory = false;
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("model.reference")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (itemref == null)? null : itemref.
                      getDocumentModel().getName();
                helper.obligatory = false;
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.sh")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)? null :
                    modelitem.getSearchHelp();
                helper.obligatory = false;
                
                newField(helper);
            }
        }
    }
    
    /**
     * 
     * @param helper
     * @return
     */
    private static final Element newField(FieldHelper helper) {
        InputComponent input;
        Table table = helper.item.getTable();
        Element element = Shell.factory(table, helper.type, helper.name, null);
        
        element.setEnabled((helper.mode == Common.SHOW)? false : true);
        
        input = (InputComponent)element;
        input.setValue(helper.value);
        input.setDataElement(helper.reference);
        input.setObligatory(helper.obligatory);
        
        helper.item.add(element);
        
        return element;
    }
    
    /**
     * 
     * @param item
     * @param name
     * @param value
     */
    public static final void setTableValue(TableItem item, String name,
            String value) {
        ((InputComponent)item.get(name)).setValue(value);
    }
}

class FieldHelper {
    public Const type;
    public int mode;
    public TableItem item;
    public String name, value;
    public DataElement reference;
    public boolean obligatory;
}
