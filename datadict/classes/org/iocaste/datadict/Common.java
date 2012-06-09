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
import org.iocaste.shell.common.View;

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
        DATA_ELEMENT("item.element", "DATAELEMENT.NAME"),
        TYPE("item.type", "DATAELEMENT.TYPE"),
        LENGTH("item.length", "DATAELEMENT.LENGTH"),
        DECIMALS("item.dec", "DATAELEMENT.DECIMALS"),
        UPCASE("item.upcase", "DATAELEMENT.UPCASE"),
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
    public static final byte getMode(View vdata) {
        return vdata.getParameter("mode");
    }
    
    /**
     * 
     * @param item
     * @param name
     * @return
     */
    public static final <T> T getTableValue(TableItem item, String name) {
        InputComponent input = item.get(name);
        
        return input.get();
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public static final int getTpObjectValue(View view) {
        RadioButton tpobj = view.getElement("tpobject");
        
        tpobj = tpobj.getSelectedComponent();
        
        return tpobj.index();
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    public static final boolean hasItemDuplicated(View vdata) {
        String name, classfield, tablefield;
        String testname, testclassfield, testtablefield;
        Table itens = vdata.getElement("itens");
        
        for (TableItem item : itens.getItens()) {
            name = getTableValue(item, "item.name");
            classfield = getTableValue(item, "item.classfield");
            tablefield = getTableValue(item, "item.tablefield");
            
            for (TableItem test : itens.getItens()) {
                if (item == test)
                    continue;
                
                testname = getTableValue(test, "item.name");
                testclassfield = getTableValue(test, "item.classfield");
                testtablefield = getTableValue(test, "item.tablefield");
                
                if (name.equals(testname)) {
                    vdata.message(Const.ERROR, "item.name.duplicated");
                    return true;
                }

                if (!Shell.isInitial(classfield) &&
                        classfield.equals(testclassfield)) {
                    vdata.message(Const.ERROR, "item.classfield.duplicated");
                    return true;
                }
                
                if (!Shell.isInitial(tablefield) &&
                        tablefield.equals(testtablefield)) {
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
        View view = config.getView();
        
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
                helper.value = (modelitem == null)? null : modelitem.getName();
                helper.obligatory = true;

                if (view != null)
                    view.setFocus(newField(helper));
                else
                    newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.length")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null : dataelement.getLength();
                helper.obligatory = true;

                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.dec")) {
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null : dataelement.getDecimals();
                helper.obligatory = false;
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.key")) {
                helper.type = Const.CHECKBOX;
                helper.obligatory = false;
                
                if (modelitem != null) {
                    model = modelitem.getDocumentModel();
                    helper.value =  model.isKey(modelitem);
                } else {
                    helper.value = false;
                }
                
                newField(helper);
                
                continue;
            }
            
            if (helper.name.equals("item.element")) {
                helper.obligatory = false;
                helper.type = Const.TEXT_FIELD;
                helper.value = (modelitem == null)?
                        null : dataelement.getName();
                
                newField(helper).setEnabled(false);
                
                continue;
            }
            
            if (helper.name.equals("item.type")) {
                helper.obligatory = false;
                helper.type = Const.LIST_BOX;
                helper.value = (modelitem == null)?
                        null : dataelement.getType();
                
                list = (ListBox)newField(helper);
                list.add("char", DataType.CHAR);
                list.add("numc", DataType.NUMC);
                list.add("dec", DataType.DEC);
                list.add("date", DataType.DATE);
                list.add("bool", DataType.BOOLEAN);
                
                continue;
            }
            
            if (helper.name.equals("item.upcase")) {
                helper.type = Const.CHECKBOX;
                helper.obligatory = false;
                
                if (modelitem != null) {
                    model = modelitem.getDocumentModel();
                    helper.value =  dataelement.isUpcase();
                } else {
                    helper.value = false;
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
        Element element = Shell.factory(table, helper.type, helper.name);
        
        element.setEnabled((helper.mode == Common.SHOW)? false : true);
        
        input = (InputComponent)element;
        input.set(helper.value);
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
            Object value) {
        ((InputComponent)item.get(name)).set(value);
    }
}

class FieldHelper {
    public Const type;
    public int mode;
    public TableItem item;
    public String name;
    public Object value;
    public DataElement reference;
    public boolean obligatory;
}
