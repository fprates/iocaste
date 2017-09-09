package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.documents.dataelement.GetDataElement;
import org.iocaste.kernel.runtime.shell.ProcessOutputData;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Input {
    private Map<String, DataElement> des;
    private ProcessOutputData outputdata;
    private Container container;
    private GetDataElement dataelementget;
    private Connection connection;
    
    public Input(ProcessOutputData outputdata, Container container)
            throws Exception {
        this.outputdata = outputdata;
        this.container = container;
        
        dataelementget = outputdata.viewctx.function.get("get_data_element");
        connection = outputdata.viewctx.function.documents.database.
                getDBConnection(outputdata.viewctx.sessionid);
        des = new HashMap<>();
    }
    
    /**
     * 
     * @param input
     * @param inputdata
     */
    private final void generateCalendar(
            InputComponent input, Container container) {
        generateCalendar(outputdata.viewctx.view, container, input);
    }
    
    public static final void generateCalendar(
            View view, Container container, InputComponent input) {
        Calendar master, earlycal, latecal;
        String htmlname, early, late;
        Map<String, Element> elements;
        
        htmlname = input.getHtmlName().concat(".cal");
        master = new Calendar(container, htmlname);
        master.setEarly(early = "early_".concat(htmlname));
        master.setLate(late = "late_".concat(htmlname));
        input.setCalendar(master);
        
        earlycal = new Calendar(container, early, Calendar.EARLY);
        earlycal.setMaster(htmlname);
        earlycal.setText("<");
        
        latecal = new Calendar(container, late, Calendar.LATE);
        latecal.setMaster(htmlname);
        latecal.setText(">");
        
        if (!container.isMultiLine())
            return;
        
        elements = view.getElements();
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
            Container container) {
//        SearchHelp sh, search;
//        String shname, name, htmlname, nsreference;
//        SearchHelpData shdata;
//        
//        shname = input.getModelItem().getSearchHelp();
//        shdata = new SHLib(inputdata.viewctx.function).get(shname);
//        if (shdata == null)
//            return;
//        
//        name = input.getName();
//        htmlname = input.getHtmlName();
//        nsreference = input.getNSReference();
//        
//        sh = new SearchHelp(inputdata.container, name.concat(".sh"));
//        sh.setHtmlName(htmlname.concat(".sh"));
//        sh.setModelName(shdata.getModel());
//        sh.setExport(shdata.getExport());
//        sh.setNSReference(nsreference);
//        sh.setCriteria(shdata.getWhere());
//        
//        search = new SearchHelp(inputdata.container, name.concat(".search"));
//        search.setHtmlName(htmlname.concat(".search"));
//        search.setMaster(sh.getHtmlName());
//        search.setNSReference(nsreference);
//        
//        sh.setChild(search.getHtmlName());
//        for (String key : shdata.getItems().keySet())
//            sh.addModelItemName(key);
//        
//        input.setSearchHelp(sh);
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
 
    public final void register(Element element, boolean enablecustom)
            throws Exception {
        String name;
        DataElement dataelement;
        Set<Element> elements;
        Container container;
        InputComponent input;
        DocumentModelItem modelitem;
        
        if (element == null)
            return;
        
        element.setView(outputdata.viewctx.view);
        if (element.isContainable()) {
            container = (Container)element;
            elements = (container.isMultiLine())?
                    getMultiLineElements(container) : container.getElements();
            for (Element child : elements)
                register(child, enablecustom);
            return;
        }
        
        if (element.isDataStorable()) {
            input = (InputComponent)element;
            modelitem = input.getModelItem();
            if (input.getSearchHelp() == null && modelitem != null &&
                    modelitem.getSearchHelp() != null)
                generateSearchHelp(input, this.container);
            
            dataelement = input.getDataElement();
            if ((dataelement != null) && dataelement.isDummy()) {
                name = dataelement.getName();
                dataelement = des.get(name);
                if (dataelement == null) {
                    dataelement = dataelementget.run(connection, name);
                    des.put(name, dataelement);
                }
                input.setDataElement(dataelement);
            }
            
            container = input.getContainer();
            if ((dataelement != null) && (input.getCalendar() == null) &&
                    (dataelement.getType() == DataType.DATE))
                generateCalendar(input, container);
        }
        
//        if (inputdata.element.hasMultipartSupport())
//            inputdata.pagectx.mpelements.add(
//                    (MultipartElement)inputdata.element);
    }
    
}
