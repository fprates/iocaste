package org.iocaste.internal;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
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

    public final void register() {
        InputData data = new InputData();

        data.container = container;
        data.element = element;
        data.function = function;
        data.view = view;
        
        register(data);
    }
    
    /**
     * 
     * @param input
     * @param inputdata
     */
    private final void generateSearchHelp(InputComponent input,
            InputData inputdata) {
        SearchHelp sh;
        SHLib shlib = new SHLib(inputdata.function);
        String shname = input.getModelItem().getSearchHelp();
        ExtendedObject[] shdata = shlib.get(shname);
        
        if (shdata == null || shdata.length == 0)
            return;
        
        sh = new SearchHelp(inputdata.container, input.getName()+".sh");
        sh.setHtmlName(input.getHtmlName()+".sh");
        sh.setModelName((String)shdata[0].get("MODEL"));
        sh.setExport((String)shdata[0].get("EXPORT"));
        
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
    private final Element[] getMultiLineElements(Container container) {
        byte selectiontype;
        Element element;
        SearchHelp sh;
        Table table;
        TableColumn[] columns;
        List<Element> elements = new ArrayList<>();
        String name, linename, htmlname, markname = null;
        int i = 0;
        
        if (container.getType() != Const.TABLE)
            new RuntimeException("Multi-line container not supported.");
        
        table = (Table)container;
        name = table.getName();
        selectiontype = table.getSelectionType();
        
        if (selectiontype == Table.SINGLE)
            markname = new StringBuilder(name).append(".mark").toString();
        
        columns = table.getColumns();
        elements = new ArrayList<Element>();
        
        for (TableItem item : table.getItems()) {
            linename = new StringBuilder(name).append(".").append(i++).
                    append(".").toString();
            
            for (TableColumn column: columns) {
                element = (column.isMark())?
                        item.get("mark") : item.get(column.getName());
                
                if (element == null)
                    continue;
                
                if (column.isMark() && markname != null)
                    htmlname = markname;
                else
                    htmlname = new StringBuilder(linename).
                            append(element.getName()).toString();
                
                element.setHtmlName(htmlname);
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
        
        return elements.toArray(new Element[0]);
    }
    
    /**
     * 
     * @param inputdata
     */
    private final void register(InputData inputdata) {
        RangeInputComponent rinput;
        Element[] elements;
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
            inputdata.view.addInput(input.getHtmlName());
            
            modelitem = input.getModelItem();
            if (input.getSearchHelp() == null && modelitem != null &&
                    modelitem.getSearchHelp() != null)
                generateSearchHelp(input, inputdata);
            
            if (input.isValueRangeComponent()) {
                rinput = (RangeInputComponent)input;
                inputdata.view.addInput(rinput.getHighHtmlName());
                inputdata.view.addInput(rinput.getLowHtmlName());
            }
        }
        
        if (inputdata.element.hasMultipartSupport())
            inputdata.view.addMultipartElement(
                    (MultipartElement)inputdata.element);
    }
    
}

class InputData {
    public View view;
    public Element element;
    public Container container;
    public Function function;
}