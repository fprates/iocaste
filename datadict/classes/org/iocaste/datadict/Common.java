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
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Common {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    
    public static final byte MODELNAME = 0;
    public static final byte MODELCLASS = 1;
    public static final byte MODELTABLE = 2;
    
    public static final boolean OBLIGATORY = true;
    public static final boolean NON_OBLIGATORY = false;
    
    public static final byte TABLE = 0;
    public static final byte SH = 1;
    
    public enum ItensNames {
        NAME("item.name", "DATAELEMENT.NAME"),
        TABLE_FIELD("item.tablefield", "MODELITEM.FIELDNAME"),
        CLASS_FIELD("item.classfield", "MODELITEM.ATTRIB"),
        KEY("item.key", null), 
        TYPE("item.type", null),
        LENGTH("item.length", "DATAELEMENT.LENGTH"),
        UPCASE("item.upcase", null);
        
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
     * @return
     */
    public static final Map<ItensNames, DataElement> getFieldReferences(
            Function function)
            throws Exception {
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
        return (Byte)vdata.getParameter("mode");
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
        if (modo == Common.SHOW)
            return ((Text)item.get(name)).getText();
        else
            return ((InputComponent)item.get(name)).getValue();
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public static final int getTpObjectValue(ViewData view) {
        RadioButton tpobj = (RadioButton)view.getElement("tpobject");
        
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
        Table itens = (Table)vdata.getElement("itens");
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

                if (classfield.equals(testclassfield)) {
                    vdata.message(Const.ERROR, "item.classfield.duplicated");
                    return true;
                }
                
                if (tablefield.equals(testtablefield)) {
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
    public static final void insertItem(Table itens, byte mode,
            DocumentModelItem modelitem,
            Map<ItensNames, DataElement> references) {
        ListBox list;
        DocumentModel model;
        DataElement dataelement = (modelitem == null)?
                null : modelitem.getDataElement();
        FieldHelper helper = new FieldHelper();
        
        helper.item = new TableItem(itens);
        helper.mode = mode;
        
        for (ItensNames itemname : ItensNames.values()) {
            helper.name = itemname.getName();
            helper.reference = references.get(itemname);
            
            if (helper.name.equals("item.tablefield")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null:modelitem.getTableFieldName();
                helper.obligatory = Common.OBLIGATORY;
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.classfield")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null:modelitem.getAttributeName();
                helper.obligatory = Common.OBLIGATORY;

                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.name")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?null:modelitem.getName();
                helper.obligatory = Common.OBLIGATORY;

                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.length")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?null:Integer.toString(
                        dataelement.getLength());
                helper.obligatory = Common.OBLIGATORY;

                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.key")) {
                helper.type = Const.CHECKBOX;
                helper.obligatory = Common.NON_OBLIGATORY;
                
                if (modelitem != null) {
                    model = modelitem.getDocumentModel();
                    if (mode == Common.SHOW)
                        helper.value = model.isKey(modelitem)? "yes" : "no";
                    else
                        helper.value =  model.isKey(modelitem)? "on" : "off";
                } else {
                    helper.value = (mode == Common.SHOW)? "no" : "off";
                }
                
                newField(helper);
                
                continue;
            }
        
            if (helper.name.equals("item.type")) {
                helper.obligatory = Common.NON_OBLIGATORY;
                
                if (mode == Common.SHOW) {
                    helper.type = Const.TEXT;
                    
                    switch (dataelement.getType()) {
                    case 0:
                        helper.value = "char";
                        break;
                    case 3:
                        helper.value = "numc";
                        break;
                    default:
                        helper.value = "?";
                        break;
                    }
                    
                    newField(helper);
                    
                } else {
                    helper.type = Const.LIST_BOX;
                    helper.value = (modelitem == null)?null:Integer.toString(
                            dataelement.getType());
                    
                    list = (ListBox)newField(helper);
                    list.add("char", Integer.toString(DataType.CHAR));
                    list.add("numc", Integer.toString(DataType.NUMC));
                }
                
                continue;
            }
            
            if (helper.name.equals("item.upcase")) {
                helper.type = Const.CHECKBOX;
                helper.obligatory = Common.NON_OBLIGATORY;
                
                if (modelitem != null) {
                    model = modelitem.getDocumentModel();
                    if (mode == Common.SHOW)
                        helper.value = dataelement.isUpcase()? "yes" : "no";
                    else
                        helper.value =  dataelement.isUpcase()? "on" : "off";
                } else {
                    helper.value = (mode == Common.SHOW)? "no" : "off";
                }
                
                newField(helper);
                
                continue;
            }
        }
    }
    
    /**
     * 
     * @param helper
     * @return
     */
    private static final Element newField(FieldHelper helper) {
        Element element;
        InputComponent input;
        Table table = helper.item.getTable();
        
        if (helper.mode == Common.SHOW) {
            element = Shell.factory(table, Const.TEXT, helper.name, null);
            ((Text)element).setText(helper.value);
        } else {
            element = Shell.factory(table, helper.type, helper.name, null);
            
            input = (InputComponent)element;
            input.setValue(helper.value);
            input.setDataElement(helper.reference);
            input.setObligatory(helper.obligatory);
        }
        
        helper.item.add(element);
        
        return element;
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
