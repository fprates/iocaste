package org.iocaste.external;

import java.util.Iterator;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
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
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void add(ViewData view) {
        Table attributes = view.getElement("attribs");
        TableItem item = new TableItem(attributes);
        
        item.add(new TextField(attributes, "aname"));
        item.add(new TextField(attributes, "avalue"));
    }
    
    /**
     * 
     * @param factory
     * @param method
     * @param message
     */
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
        ExternalProperty eproperty;
        ServiceClient client;
        OMElement result;
        Options options;
        InputComponent input;
        String name;
        String namespace = Common.getInput(view, "namespace");
        String service = Common.getInput(view, "service");
        String url = Common.getInput(view, "url");
        String method = Common.getInput(view, "method");
        Table attributes = view.getElement("attribs");
        
        try {
            epr = new EndpointReference(url);
            factory = OMAbstractFactory.getOMFactory();
            ping = getMethod(factory, method, namespace, service);
            
            emessage = new ExternalMessage();
            for (TableItem item : attributes.getItens()) {
                input = (InputComponent)item.get("name");
                name = input.get();
                
                if (Shell.isInitial(name))
                    continue;
                
                eproperty = new ExternalProperty();
                eproperty.setName(name);
                
                input = (InputComponent)item.get("value");
                eproperty.setValue((String)input.get());
                
                emessage.add(eproperty);
            }
            
            addMessage(factory, ping, emessage);
            
            options = new Options();
            options.setTo(epr);
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            client = new ServiceClient();
            client.setOptions(options);
            
            result = client.sendReceive(ping);
        } catch (AxisFault e) {
            throw new Exception(e.getMessage());
        }
        
        emessage = new ExternalMessage();
        convertToMessage(result, emessage, null);

        view.setReloadableView(true);
        view.export("map", emessage.toMap());
        view.redirect(null, "output");
    }
    
    /**
     * 
     * @param node
     * @param message
     * @param property
     */
    private final void convertToMessage(OMNode node,
            ExternalMessage message, ExternalProperty property) {
        OMNode node_;
        OMElement element;
        String name;
        OMAttribute attribute;
        Iterator<?> it;
        
        if (node.getType() != 1)
            return;
        
        element = (OMElement)node;
        name = element.getLocalName();
        if (name.equals("name"))
            property.setName(element.getText());
        
        if (name.equals("value"))
            property.setValue(element.getText());
        
        it = element.getAllAttributes();
        while (it.hasNext()) {
            attribute = (OMAttribute)it.next();
            if (!attribute.getLocalName().equals("type"))
                continue;
            
            name = attribute.getAttributeValue().split(":")[1];
            if (name.equals("ExternalMessage"))
                continue;
            
            if (name.equals("ExternalProperty")) {
                property = new ExternalProperty();
                message.add(property);
            }
        }
        
        it = element.getChildren();
        while (it.hasNext()) {
            node_ = (OMNode)it.next();
            convertToMessage(node_, message, property);
        }
    }
    
    private final OMElement getMethod(OMFactory factory, String methodname,
            String namespace, String service) {
        OMNamespace ns = factory.createOMNamespace(namespace, service);
        
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
        Table attributes;
        DataItem dataitem;
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        
        dataitem = new DataItem(form, Const.TEXT_FIELD, "namespace");
        dataitem.setLength(80);
        dataitem.set("http://service.external.iocaste.org");
        
        new DataItem(form, Const.TEXT_FIELD, "service");
        new DataItem(form, Const.TEXT_FIELD, "url").setLength(80);
        new DataItem(form, Const.TEXT_FIELD, "method");
        
        new Button(container, "add");
        new Button(container, "remove");
        
        attributes = new Table(container, "attribs");
        attributes.setMark(true);
        new TableColumn(attributes, "name");
        new TableColumn(attributes, "value");
        
        new Button(container, "call");
        
        view.setFocus("service");
        view.setReloadableView(true);
        view.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     */
    public final void output(ViewData view) {
        Map<String, String> map = view.getParameter("map");
        
        for (String key : map.keySet())
            view.print(key+": "+map.get(key));
        
        view.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     */
    public final void remove(ViewData view) {
        Table attributes = view.getElement("attribs");
        
        for (TableItem item : attributes.getItens())
            if (item.isSelected())
                attributes.remove(item);
    }
}
