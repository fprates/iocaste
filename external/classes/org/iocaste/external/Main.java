package org.iocaste.external;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.iocaste.external.service.ExternalElement;
import org.iocaste.external.service.ExternalViewData;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final String NAMESPACE =
            "http://service.external.iocaste.org";
    private static final String SERVICE = "ExternalView";
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void action(ControlData controldata, ViewData view) {
        
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
    private final ExternalViewData extractExternalElements(
            Object object, OMElement element) {
        OMNode node_;
        String elementname;
        ExternalElement econtainer;
        String name = null;
        String type = null;
        String[] parameters = null;
        Iterator<?> it = element.getChildren();
        List<ExternalElement> containers = null;
        int level = 0;
        
        while (it.hasNext()) {
            node_ = (OMNode)it.next();
            if (node_.getType() != 1)
                continue;
            
            element = (OMElement)node_;
            elementname = element.getLocalName();
            
            if (elementname.equals("return")) {
                extractExternalElements(object, element);
                continue;
            }
            
            if (elementname.equals("containers")) {
                extractExternalElements(object, element);
                
                level = 10;
                continue;
            }
            
            if (elementname.equals("elements")) {
                parameters = new String[2];
                extractExternalElements(parameters, element);
                
                containers = (List<ExternalElement>)object;
                level = 1;
                
                continue;
            }
            
            if (elementname.equals("name")) {
                switch (level) {
                case 0:
                    parameters = (String[])object;
                    parameters[1] = element.getText();
                    
                    break;
                case 1:
                case 10:
                    name = element.getText();
                    
                    break;
                }
                
                level++;
                
                continue;
            }
            
            if (elementname.equals("type")) {
                switch (level) {
                case 1:
                    parameters = (String[])object;
                    parameters[0] = element.getText();
                    
                    break;
                case 2:
                case 11:
                    type = element.getText();
                    
                    break;
                }
                
                level++;
                
                continue;
            }
        }
        
        switch (level) {
        case 3:
            econtainer = new ExternalElement(null, type, name);
            new ExternalElement(econtainer, parameters[0], parameters[1]);
            
            containers.add(econtainer);
            
            return null;
            
        case 11:
            return ExternalViewData.build(name,
                    containers.toArray(new ExternalElement[0]));
            
        default:
            return null;
        }
            
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
        
        return extractExternalElements(econtainers, element);
    }
    
    /**
     * 
     * @param view
     * @throws Exception 
     */
    public void main(ViewData view) throws Exception {
//        String url = (String)view.getParameter("service");
        String url = "http://localhost:8080/axis2/services/TestView";
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
        String type = eelement.getType();
        String name = eelement.getName();
        
        if (type.equals("std_container"))
            return new StandardContainer(container, name);
        
        if (type.equals("form"))
            return new Form(container, name);
        
        if (type.equals("data_form"))
            return new DataForm(container, name);
        
        if (type.equals("table"))
            return new Table(container, 0, name);
        
        return null;
    }
    
    private final void rebuildView(ExternalViewData eview, ViewData view) {
        view.setTitle(eview.getTitle());
        
        for (ExternalElement element : eview.getContainers()) 
            view.addContainer(rebuildContainer(null, element));
    }
}
