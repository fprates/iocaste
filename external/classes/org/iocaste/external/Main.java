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
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
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
    private final ExternalViewData extractExternalElements(int level,
            Object object, OMElement element) {
        OMNode node_;
        String elementname;
        ExternalElement econtainer;
        ExternalViewData view;
        List<String[]> eparameters = null;
        String title = null;
        String name = null;
        String type = null;
        String[] parameters = null;
        Iterator<?> it = element.getChildren();
        List<ExternalElement> containers = null;
        
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
         */
        
        switch (level) {
        case 2:
            eparameters = new ArrayList<String[]>();
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
                parameters = new String[2];
                
                extractExternalElements(3, parameters, element);
                eparameters.add(parameters);
                
                continue;
            }
            
            if (elementname.equals("name")) {
                switch (level) {
                case 3:
                    parameters = (String[])object;
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
                    parameters = (String[])object;
                    parameters[0] = element.getText();
                    
                    continue;
                default:
                    type = element.getText();
                    
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

            for (String[] parameters_ : eparameters)
                new ExternalElement(
                        econtainer, parameters_[0], parameters_[1]);

            containers = (List<ExternalElement>)object;
            containers.add(econtainer);
            
            break;
            
        case 1:
            containers = (List<ExternalElement>)object;
            
            view = ExternalViewData.build(name,
                    containers.toArray(new ExternalElement[0]));
            
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
        
        if (type.equals("button"))
            return new Button(container, name);
        
        if (type.equals("check_box"))
            return new CheckBox(container, name);
        
        if (type.equals("form"))
            return new Form(container, name);
        
        if (type.equals("data_form"))
            return new DataForm(container, name);
        
        if (type.equals("std_container"))
            return new StandardContainer(container, name);
        
        if (type.equals("table"))
            return new Table(container, 0, name);
        
        if (type.equals("table_item"))
            return new TableItem((Table)container);
        
        if (type.equals("text"))
            return new Text(container, name);
        
        if (type.equals("text_field"))
            return new TextField(container, name);
        
        return null;
    }
    
    private final void rebuildView(ExternalViewData eview, ViewData view) {
        view.setTitle(eview.getTitle());
        
        for (ExternalElement element : eview.getContainers()) 
            view.addContainer(rebuildContainer(null, element));
    }
}
