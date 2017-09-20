package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.kernel.documents.dataelement.GetDataElement;
import org.iocaste.kernel.runtime.shell.ProcessOutputData;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class Input {
    private Map<String, DataElement> des;
    private ProcessOutputData outputdata;
    private GetDataElement dataelementget;
    private Connection connection;
    
    public Input(ProcessOutputData outputdata)
            throws Exception {
        this.outputdata = outputdata;
        
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
        generateCalendar(outputdata.viewctx, container, input);
    }
    
    public static final void generateCalendar(
            ViewContext viewctx, Container container, InputComponent input) {
        Calendar master, earlycal, latecal;
        String htmlname, early, late, parent;
        Map<String, Element> elements;
        
        parent = container.getHtmlName();
        htmlname = input.getHtmlName().concat(".cal");
        viewctx.instance(null, htmlname).parent = parent;
        master = new Calendar(viewctx, htmlname, 0);
        master.setEarly(early = "early_".concat(htmlname));
        master.setLate(late = "late_".concat(htmlname));
        input.setCalendar(master);

        viewctx.instance(null, early).parent = parent;
        earlycal = new Calendar(viewctx, early, Calendar.EARLY);
        earlycal.setMaster(htmlname);
        earlycal.setText("<");

        viewctx.instance(null, late).parent = parent;
        latecal = new Calendar(viewctx, late, Calendar.LATE);
        latecal.setMaster(htmlname);
        latecal.setText(">");
        
        if (!container.isMultiLine())
            return;
        
        elements = viewctx.view.getElements();
        elements.put(htmlname, master);
        elements.put(early, earlycal);
        elements.put(late, latecal);
    }
    
    /**
     * 
     * @param input
     * @param inputdata
     */
    private final void generateSearchHelp(InputComponent input) {
        SearchHelp sh, search;
        String shname, name, htmlname, nsreference;
        SearchHelpData shdata;
        
        shname = input.getModelItem().getSearchHelp();
        shdata = new SHLib(outputdata.viewctx.function).get(shname);
        if (shdata == null)
            return;
        
        name = input.getName();
        htmlname = input.getHtmlName();
        nsreference = input.getNSReference();

        outputdata.viewctx.instance(null, shname = name.concat(".sh"));
        sh = new SearchHelp(outputdata.viewctx, shname);
        sh.setHtmlName(htmlname.concat(".sh"));
        sh.setModelName(shdata.getModel());
        sh.setExport(shdata.getExport());
        sh.setNSReference(nsreference);
        sh.setCriteria(shdata.getWhere());

        outputdata.viewctx.instance(null, shname = name.concat(".search"));
        search = new SearchHelp(outputdata.viewctx, shname);
        search.setHtmlName(htmlname.concat(".search"));
        search.setMaster(sh.getHtmlName());
        search.setNSReference(nsreference);
        
        sh.setChild(search.getHtmlName());
        for (String key : shdata.getItems().keySet())
            sh.addModelItemName(key);
        
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
            container = input.getContainer();
            if (input.getSearchHelp() == null && modelitem != null &&
                    modelitem.getSearchHelp() != null)
                generateSearchHelp(input);
            
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
            
            if ((dataelement != null) && (input.getCalendar() == null) &&
                    (dataelement.getType() == DataType.DATE))
                generateCalendar(input, container);
        }
        
//        if (inputdata.element.hasMultipartSupport())
//            inputdata.pagectx.mpelements.add(
//                    (MultipartElement)inputdata.element);
    }
    
}
