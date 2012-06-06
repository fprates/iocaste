package org.iocaste.external;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.iocaste.external.service.ExternalMessage;
import org.iocaste.external.service.ExternalProperty;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final String NAMESPACE =
            "http://service.external.iocaste.org";
    private static final String SERVICE = "Ping";
    
    public Main() {
        export("install", "install");
    }
    
    private final void addMessage(OMFactory factory, OMElement method,
            ExternalMessage message) {
        OMElement xmlvalues;
        OMNamespace ns = method.getNamespace();
        OMElement xmlmessage = factory.createOMElement("message", ns);
        
        for (ExternalProperty property : message.getValues()) {
            xmlvalues = factory.createOMElement("values", ns);
            
            for (OMElement xmlproperty : getProperty(factory, ns, property))
                xmlvalues.addChild(xmlproperty);
            
            xmlmessage.addChild(xmlvalues);
        }
        
        method.addChild(xmlmessage);
    }
    
    /**
     * 
     * @param view
     */
    public final void call(ViewData view) throws Exception {
        EndpointReference epr;
        OMFactory factory;
        OMElement ping;
        ExternalMessage emessage;
        ServiceClient service;
        OMElement result;
        Options options;
        String url = ((InputComponent)view.getElement("url")).get();
        String method = ((InputComponent)view.getElement("method")).get();
        
        try {
            epr = new EndpointReference(url);
            factory = OMAbstractFactory.getOMFactory();
            ping = getMethod(factory, method);
            emessage = new ExternalMessage();
            
            addMessage(factory, ping, emessage);
            
            options = new Options();
            options.setTo(epr);
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            service = new ServiceClient();
            service.setOptions(options);
            
            result = service.sendReceive(ping);
        } catch (AxisFault e) {
            throw new Exception(e.getMessage());
        }
        
        view.setReloadableView(true);
        view.export("print", printElement("-", result));
        view.redirect(null, "output");
    }
    
    private final OMElement getMethod(
            OMFactory factory, String methodname) {
        OMNamespace ns = factory.createOMNamespace(NAMESPACE, SERVICE);
        
        return factory.createOMElement(methodname, ns); 
    }
    
    private final OMElement[] getProperty(OMFactory factory, OMNamespace ns,
            ExternalProperty property) {
        OMElement[] xmlproperty = new OMElement[2];
        
        xmlproperty[0] = factory.createOMElement("name", ns);
        xmlproperty[0].addChild(factory.createOMText(property.getName()));
        
        xmlproperty[1] = factory.createOMElement("value", ns);
        xmlproperty[1].addChild(factory.createOMText(
                (String)property.getValue()));
        
        return xmlproperty;
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        
        new DataItem(form, Const.TEXT_FIELD, "url").setLength(80);
        new DataItem(form, Const.TEXT_FIELD, "method");
        
        new Button(container, "call");
        
        view.setFocus("url");
        view.setReloadableView(true);
        view.setNavbarActionEnabled("back", true);
    }
    
    public final void output(ViewData view) {
        List<String> list = view.getParameter("print");
        
        for (String line : list)
            view.print(line);
        
        view.setNavbarActionEnabled("back", true);
    }
    
    private final List<String> printElement(String level, OMNode node) {
        OMElement element;
        OMText text;
        OMNode node_;
        OMAttribute attribute;
        Iterator<?> it;
        List<String> print = new ArrayList<String>();
        
        switch (node.getType()) {
        case 1:
            element = (OMElement)node;
            print.add(level+" Element: "+element.getLocalName()+
                    " - type: "+node.getType()+" - text: "+element.getText());
            
            it = element.getAllAttributes();
            while (it.hasNext()) {
                attribute = (OMAttribute)it.next();
                print.add(level+" *"+attribute.getLocalName()+": "+attribute.
                        getAttributeValue());
            }
            
            it = element.getChildren();
            
            while (it.hasNext()) {
                node_ = (OMNode)it.next();
                print.addAll(printElement(level+"-", node_));
            }
            
            break;
            
        case 4:
            text = (OMText)node;
            print.add(level+" Text: "+text.getText()+" - type: "+node.
                    getType());
            
            break;
        default:
            print.add(level+" unknown - type: "+node.getType());
            
            break;
        }
        
        return print;
    }
}
