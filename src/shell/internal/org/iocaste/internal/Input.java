package org.iocaste.internal;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Input {
    public View view;
    public Element element;
    public Container container;
    public Function function;
    public boolean enablecustom;
    public PageContext pagectx;
    private Documents documents;
    private Map<String, DataElement> des;

    public final void register() {
        InputData data = new InputData();

        data.container = container;
        data.element = element;
        data.function = function;
        data.view = view;
        data.enablecustom = enablecustom;
        data.pagectx = pagectx;
        
        documents = new Documents(function);
        des = new HashMap<>();
        register(data);
    }
    
    /**
     * 
     * @param input
     * @param inputdata
     */
    private final void generateCalendar(InputComponent input,
            InputData inputdata) {
        Calendar master, earlycal, latecal;
        String htmlname, early, late;
        Map<String, Element> elements;
        
        htmlname = input.getHtmlName().concat(".cal");
        master = new Calendar(inputdata.container, htmlname);
        master.setEarly(early = "early_".concat(htmlname));
        master.setLate(late = "late_".concat(htmlname));
        input.setCalendar(master);
        
        earlycal = new Calendar(inputdata.container, early, Calendar.EARLY);
        earlycal.setMaster(htmlname);
        earlycal.setText("<");
        
        latecal = new Calendar(inputdata.container, late, Calendar.LATE);
        latecal.setMaster(htmlname);
        latecal.setText(">");
        
        if (!inputdata.container.isMultiLine())
            return;
        
        elements = inputdata.view.getElements();
        elements.put(htmlname, master);
        elements.put(early, earlycal);
        elements.put(late, latecal);
    }
    
    /**
     * 
     * @param input
     * @param inputdata
     */
    private final void generateSearchHelp(InputComponent input,
            InputData inputdata) {
        SearchHelp sh, search;
        String shname, name, htmlname, nsreference;
        ExtendedObject[] shdata;
        
        shname = input.getModelItem().getSearchHelp();
        shdata = new SHLib(inputdata.function).get(shname);
        
        if (shdata == null || shdata.length == 0)
            return;
        
        name = input.getName();
        htmlname = input.getHtmlName();
        nsreference = input.getNSReference();
        sh = new SearchHelp(inputdata.container, name.concat(".sh"));
        sh.setHtmlName(htmlname.concat(".sh"));
        sh.setModelName((String)shdata[0].get("MODEL"));
        sh.setExport((String)shdata[0].get("EXPORT"));
        sh.setNSReference(nsreference);
        
        search = new SearchHelp(inputdata.container, name.concat(".search"));
        search.setHtmlName(htmlname.concat(".search"));
        search.setMaster(sh.getHtmlName());
        search.setNSReference(nsreference);
        
        sh.setChild(search.getHtmlName());
        for (int i = 1; i < shdata.length; i++) {
            shname = shdata[i].get("ITEM");
            sh.addModelItemName(shname);
        }
        
        input.setSearchHelp(sh);
    }
    
    /**
     * 
     * @param container
     * @return
     */
    private final Set<Element> getMultiLineElements(Container container) {
        Element element;
        SearchHelp sh;
        Table table;
        TableColumn[] columns;
        Set<Element> elements;
        String name, linename, htmlname;
        int i = 0;
        
        if (container.getType() != Const.TABLE)
            new RuntimeException("Multi-line container not supported.");
        
        table = (Table)container;
        name = table.getName();
        columns = table.getColumns();
        elements = new LinkedHashSet<>();
        
        for (TableItem item : table.getItems()) {
            linename = new StringBuilder(name).append(".").append(i++).
                    append(".").toString();
            
            for (TableColumn column: columns) {
                element = (column.isMark())?
                        item.get("mark") : item.get(column.getName());
                
                if (element == null)
                    continue;
                
                elements.add(element);
                
                /*
                 * ajusta nome de ajuda de pesquisa, se houver
                 */
                if (!element.isDataStorable())
                    continue;
                
                sh = ((InputComponent)element).getSearchHelp();
                if (sh == null)
                    continue;
                
                htmlname = new StringBuilder(linename).
                        append(sh.getName()).toString();
                
                sh.setHtmlName(htmlname);
                elements.add(sh);
            }
        }
        
        return elements;
    }
    
    /**
     * 
     * @param inputdata
     */
    private final void register(InputData inputdata) {
        String name;
        DataElement dataelement;
        RangeInputComponent rinput;
        Set<Element> elements;
        InputData inputdata_;
        Container container;
        InputComponent input;
        DocumentModelItem modelitem;
        
        if (inputdata.element == null)
            return;
        
        inputdata.element.setView(inputdata.view);
        if (inputdata.element.isContainable()) {
            container = (Container)inputdata.element;
            
            inputdata_ = new InputData();
            inputdata_.container = container;
            inputdata_.view = inputdata.view;
            inputdata_.function = inputdata.function;
            inputdata_.enablecustom = inputdata.enablecustom;
            inputdata_.inputdisabled = inputdata.inputdisabled;
            inputdata_.pagectx = inputdata.pagectx;
            
            if (!inputdata_.enablecustom && container.isRemote())
                inputdata_.inputdisabled = true;
                
            elements = (container.isMultiLine())?
                    getMultiLineElements(container) : container.getElements();
                    
            for (Element element : elements) {
                inputdata_.element = element;
                register(inputdata_);
            }
            
            return;
        }
        
        if (inputdata.element.isDataStorable()) {
            input = (InputComponent)inputdata.element;
            if (!inputdata.inputdisabled)
                inputdata.pagectx.inputs.add(input.getHtmlName());
            
            modelitem = input.getModelItem();
            if (input.getSearchHelp() == null && modelitem != null &&
                    modelitem.getSearchHelp() != null)
                generateSearchHelp(input, inputdata);
            
            dataelement = input.getDataElement();
            if ((dataelement != null) && dataelement.isDummy()) {
                name = dataelement.getName();
                dataelement = des.get(name);
                if (dataelement == null) {
                    dataelement = documents.getDataElement(name);
                    des.put(name, dataelement);
                }
                input.setDataElement(dataelement);
            }
            
            container = input.getContainer();
            if ((dataelement != null) && (input.getCalendar() == null) &&
                    (dataelement.getType() == DataType.DATE))
                generateCalendar(input, inputdata);
            
            if (input.isValueRangeComponent()) {
                rinput = (RangeInputComponent)input;
                inputdata.pagectx.inputs.add(rinput.getHighHtmlName());
                inputdata.pagectx.inputs.add(rinput.getLowHtmlName());
            }
        }
        
        if (inputdata.element.hasMultipartSupport())
            inputdata.pagectx.mpelements.add(
                    (MultipartElement)inputdata.element);
    }
    
}

class InputData {
    public View view;
    public Element element;
    public Container container;
    public Function function;
    public boolean enablecustom, inputdisabled;
    public PageContext pagectx;
}