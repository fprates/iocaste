package org.iocaste.external;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.iocaste.external.service.ExternalElement;
import org.iocaste.external.service.ExternalViewData;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final String NAMESPACE =
            "http://service.external.iocaste.org";
    private static final String SERVICE = "ExternalView";
    
    /**
     * 
     * @param view
     */
    public final void action(ViewData view) {
        
    }
    
    private final void addValue(OMFactory factory, OMElement method,
            String name, String value) {
        OMElement param = factory.createOMElement(name, method.getNamespace());
        
        param.addChild(factory.createOMText(value));
        method.addChild(param);
    }
    
    private final OMElement getMethod(
            OMFactory factory, String methodname) {
        OMNamespace ns = factory.createOMNamespace(NAMESPACE, SERVICE);
        
        return factory.createOMElement(methodname, ns); 
    }
    
    private final ExternalViewData callExternalView(String url, String page)
            throws Exception {
        ServiceClient service;
        OMElement result;
        Options options;
        EndpointReference epr = new EndpointReference(url);
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMElement getview = getMethod(factory, "getView");
        
        addValue(factory, getview, "name", page);
        
        options = new Options();
        options.setTo(epr);
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        
        service = new ServiceClient();
        service.setOptions(options);
        
        result = service.sendReceive(getview);
        
        return extractExternalView(page, result);
    }
    
    @SuppressWarnings("unchecked")
    private final ExternalViewData extractExternalElements(int level,
            Object object, OMElement element) {
        OMNode node_;
        OMText text;
        String elementname;
        ExternalElement econtainer;
        ExternalElement eelement;
        ExternalViewData view;
        Iterator<?> it, itv;
        List<Object[]> eparameters = null;
        String title = null;
        String name = null;
        String type = null;
        Object[] parameters = null;
        List<String> values = null;
        List<ExternalElement> containers = null;
        
        it = element.getChildren();
        
        /*
         * Estrutura do documento:
         * - return
         * -- containers
         * --- elements
         * ---- name
         * ---- type
         * ---- values
         * --- name
         * --- type
         * -- name
         * -- title
         * -- values
         */
        
        switch (level) {
        case 2:
            eparameters = new ArrayList<Object[]>();
            break;
        }
        
        while (it.hasNext()) {
            node_ = (OMNode)it.next();
            if (node_.getType() != 1)
                continue;
            
            element = (OMElement)node_;
            elementname = element.getLocalName();
            
            if (elementname.equals("return"))
                return extractExternalElements(1, object, element);
            
            if (elementname.equals("containers")) {
                extractExternalElements(2, object, element);
                
                continue;
            }
            
            if (elementname.equals("elements")) {
                parameters = new Object[3];
                
                extractExternalElements(3, parameters, element);
                eparameters.add(parameters);
                
                continue;
            }
            
            if (elementname.equals("name")) {
                switch (level) {
                case 3:
                    parameters = (Object[])object;
                    parameters[1] = element.getText();
                    
                    continue;
                default:
                    name = element.getText();
                    
                    break;
                }
            }
            
            if (elementname.equals("type")) {
                switch (level) {
                case 3:
                    parameters = (Object[])object;
                    parameters[0] = element.getText();
                    
                    continue;
                default:
                    type = element.getText();
                    
                    break;
                }
            }
            
            if (elementname.equals("values")) {
                values = new ArrayList<String>();
                
                itv = element.getChildren();
                while (itv.hasNext()) {
                    node_ = (OMNode)itv.next();
                    
                    if (node_.getType() != 4)
                        continue;
                    
                    text = (OMText)node_;
                    values.add(text.getText());
                }
                
                switch (level) {
                case 3:
                    parameters = (Object[])object;
                    parameters[2] = values;
                    
                    continue;
                    
                default:
                    break;
                }
            }
            
            if (elementname.equals("title"))
                title = element.getText();
        }
        
        /*
         * verifica se atende às condições de geração de objetos
         */
        switch (level) {
        case 2:
            econtainer = new ExternalElement(null, type, name);

            for (Object[] parameters_ : eparameters) {
                eelement = new ExternalElement(econtainer,
                        (String)parameters_[0], (String)parameters_[1]);
                
                if (parameters_[2] == null)
                    continue;
                
                values = (List<String>)parameters_[2];
                eelement.setValues(values.toArray(new String[0]));
            }
            
            econtainer.flushOnlyElements();
            containers = (List<ExternalElement>)object;
            containers.add(econtainer);
            
            break;
            
        case 1:
            containers = (List<ExternalElement>)object;
            
            view = new ExternalViewData(name);
            view.setValues(values.toArray(new String[0]));
            view.setContainers(containers.toArray(new ExternalElement[0]));
            view.setTitle(title);
            
            return view;
            
        default:
            break;
        }
        
        return null;
    }
    
    private final ExternalViewData extractExternalView(
            String page, OMNode node) {
        OMElement element;
        List<ExternalElement> econtainers;
        
        if (node.getType() != 1)
            return null;
        
        element = (OMElement)node;
        if (!element.getLocalName().equals("getViewResponse"))
            return null;
        
        econtainers = new ArrayList<ExternalElement>();
        
        return extractExternalElements(0, econtainers, element);
    }
    
    /**
     * 
     * @param view
     * @throws Exception 
     */
    public void main(ViewData view) throws Exception {
        String url = (String)view.getParameter("service");
        String page = (String)view.getParameter("view");
        ExternalViewData eview = callExternalView(url, page);
        
        rebuildView(eview, view);
    }
    
    private final Container rebuildContainer(Container super_,
            ExternalElement econtainer) {
        Container container = (Container)rebuildElement(super_, econtainer);
        
        for (ExternalElement eelement : econtainer.getElements())
            rebuildElement(container, eelement);
        
        return container;
    }
    
    private final Element rebuildElement(Container container,
            ExternalElement eelement) {
        String[] values;
        String[] values_;
        Component component = null;
        Element element = null;
        String type = eelement.getType();
        String name = eelement.getName();
        
        if (type.equals("button"))
            element = new Button(container, name);
        
        if (type.equals("check_box"))
            element = new CheckBox(container, name);
        
        if (type.equals("form"))
            element = new Form(container, name);
        
        if (type.equals("data_form"))
            element = new DataForm(container, name);
        
        if (type.equals("std_container"))
            element = new StandardContainer(container, name);
        
        if (type.equals("table"))
            element = new Table(container, 0, name);
        
        if (type.equals("table_item"))
            element = new TableItem((Table)container);
        
        if (type.equals("text"))
            element = new Text(container, name);
        
        if (type.equals("text_field"))
            element = new TextField(container, name);

        if (element == null)
            return null;

        if (!element.isContainable())
            component = (Component)element;
        
        values = eelement.getValues();
        if (values == null)
            return element;
        
        for (String value : values) {
            values_ = value.split(":");
            
            if (values_[0].equals("text") && component != null)
                component.setText(values_[1]);
        }
        
        return element;
    }
    
    private final void rebuildView(ExternalViewData eview, ViewData view) {
        String[] values;
        view.setTitle(eview.getTitle());
        
        for (ExternalElement element : eview.getContainers()) 
            view.addContainer(rebuildContainer(null, element));
        
        for (String value : eview.getValues()) {
            values = value.split(":");
            
            if (values[0].equals("navcontrol_enable"))
                view.setNavbarActionEnabled(values[1],
                        Boolean.parseBoolean(values[2]));
        }
    }
}
