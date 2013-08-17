package org.iocaste.external;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    Context context;
    
    public Main() {
        context = new Context();
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void add(View view) {
        Table attributes = view.getElement("attribs");
        TableItem item = new TableItem(attributes);
        
        item.add(new TextField(attributes, "aname"));
        item.add(new TextField(attributes, "avalue"));
    }
    
    /**
     * 
     * @param view
     */
    public final void call(View view) throws Exception {
        OMElement result;
        DataForm selection = view.getElement("selection");
        String wsdl = selection.get("wsdl").get();
        URL url_ = new URL(wsdl);
        Map<String, List<ElementDetail>> values = new HashMap<>();
        
        result = WSClient.getWSDLObject(url_.openStream());
        Context.convertXmlToMap(values, result, null);
        
        recoverPorts(context, values);
        view.redirect(null, "output");
    }
    
    private final Type getPrimitive(String name) {
        Type type = new Type();
        
        type.name = name;
        
        return type;
    }
    
    private final Type processComplexType(ElementDetail element) {
        Type type = null;
        
        if (element.children.size() == 0)
            return null;

        for (ElementDetail sequence : element.children)
            for (ElementDetail eelement : sequence.children) {
                type = new Type();
                type.name = eelement.attributes.get("name");
                type.min = eelement.attributes.get("minOccurs");
                type.max = eelement.attributes.get("maxOccurs");
                type.ctype = eelement.attributes.get("type");
            }
        
        return type;
    }
    
    private final Map<String, Type> processTypes(List<ElementDetail> types) {
        Type type;
        Map<String, Type> types_ = new HashMap<>();
        
        types_.put("string", getPrimitive("string"));
        
        for (ElementDetail etype : types)
            for (ElementDetail eschema : etype.children)
                for (ElementDetail eelement : eschema.children) {
                    type = null;
                    
                    switch (eelement.name) {
                    case "definitions.types.schema.element":
                        for (ElementDetail ctype : eelement.children)
                            type = processComplexType(ctype);
                        break;
                    case "definitions.types.schema.complexType":
                        type = processComplexType(eelement);
                        break;
                    }
                    
                    if (type == null)
                        continue;
                    types_.put(type.name, type);
                }
        
        return types_;
    }
    
    private final Map<String, OpMessage> processOpMessages(
            List<ElementDetail> messages, Map<String, Type> types) {
        Type type;
        String partelement;
        OpMessage opmessage;
        Map<String, OpMessage> messages_ = new HashMap<>();
        
        for (ElementDetail emessage : messages) {
            opmessage = new OpMessage();
            opmessage.name = emessage.attributes.get("name");
            messages_.put(opmessage.name, opmessage);
            
            for (ElementDetail part : emessage.children) {
                partelement = part.attributes.get("element");
                if (partelement != null)
                    partelement = partelement.split(":")[1];
                else
                    partelement = part.attributes.get("type").split(":")[1];
                
                type = types.get(partelement);
                if (type == null)
                    continue;
                
                opmessage.parameters.add(type);
            }
        }
        
        return messages_;
    }
    
    private final Map<String, PortType> processPortTypes(
            List<ElementDetail> porttypes, Map<String, OpMessage> messages) {
        String messagename, opname;
        Operation operation;
        PortType porttype;
        List<Type> types;
        Map<String, PortType> porttypes_ = new HashMap<>();
        
        for (ElementDetail eporttype : porttypes) {
            porttype = new PortType();
            porttype.name = eporttype.attributes.get("name");
            porttypes_.put(porttype.name, porttype);
            for (ElementDetail eoperation : eporttype.children) {
                opname = eoperation.attributes.get("name");
                operation = new Operation();
                operation.name = opname;
                porttype.operations.put(opname, operation);
                for (ElementDetail eopitem : eoperation.children) {
                    messagename = eopitem.attributes.get("message");
                    if (messagename == null)
                        continue;
                    
                    messagename = messagename.split(":")[1];
                    types = messages.get(messagename).parameters;
                    if (types == null)
                        continue;
                    
                    for (Type type : types)
                        operation.parameters.put(type.name, type);
                }
            }
        }
        
        return porttypes_;
    }
    
    private final Map<String, Binding> processBindings(
            List<ElementDetail> details, Map<String, PortType> porttypes) {
        Binding binding;
        String operationname, porttypename;
        Map<String, Binding> bindings_ = new HashMap<>();
        
        for (ElementDetail ebinding : details) {
            binding = new Binding();
            binding.name = ebinding.attributes.get("name");
            porttypename = ebinding.attributes.get("type").split(":")[1];
            for (ElementDetail eoperation : ebinding.children) {
                operationname = eoperation.attributes.get("name");
                binding.porttypes.put(operationname,
                        porttypes.get(porttypename));
            }
            bindings_.put(binding.name, binding);
        }
        
        return bindings_;
    }
    
    private final Map<String, Port> processService(
            List<ElementDetail> services, Map<String, Binding> bindings) {
        Port port;
        String bindingname;
        Binding binding;
        Map<String, Port> ports = new HashMap<>();
        
        for (ElementDetail service : services) {
            for (ElementDetail eport : service.children) {
                bindingname = eport.attributes.get("binding").split(":")[1];
                binding = bindings.get(bindingname);
                
                port = new Port();
                port.name = eport.attributes.get("name");
                ports.put(port.name, port);
                
                for (String porttypename : binding.porttypes.keySet())
                    port.operations.putAll(binding.porttypes.
                            get(porttypename).operations);
            }
        }
        
        return ports;
    }
    
    private final void recoverPorts(Context context,
            Map<String, List<ElementDetail>> values) {
        List<ElementDetail> details;
        Map<String, Type> types;
        Map<String, OpMessage> messages;
        Map<String, PortType> porttypes;
        Map<String, Binding> bindings;

        porttypes = new HashMap<>();
        bindings = new HashMap<>();
        
        details = values.get("definitions.types");
        types = processTypes(details);
        
        details = values.get("definitions.message");
        messages = processOpMessages(details, types);
        
        details = values.get("definitions.portType");
        porttypes = processPortTypes(details, messages);
        
        details = values.get("definitions.binding");
        bindings = processBindings(details, porttypes);
        
        details = values.get("definitions.service");
        context.ports = processService(details, bindings);
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(View view) throws Exception {
        DataItem dataitem;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        
        pagecontrol.add("home");
        dataitem = new DataItem(form, Const.TEXT_FIELD, "wsdl");
        dataitem.setLength(128);
        dataitem.setVisibleLength(128);
        dataitem.setObligatory(true);
        view.setFocus(dataitem);
        
        new Button(container, "call");
    }
    
    /**
     * 
     * @param view
     */
    public final void output(View view) {
        Operation operation;
        Port port;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("back");
        for (String portname : context.ports.keySet()) {
            view.print(portname);
            port = context.ports.get(portname);
            for (String operationname : port.operations.keySet()) {
                view.print("-- ".concat(operationname));
                operation = port.operations.get(operationname);
                for (String parameter: operation.parameters.keySet())
                    view.print("--- ".concat(parameter));
            }
        }
    }
    
    /**
     * 
     * @param view
     */
    public final void remove(View view) {
        Table attributes = view.getElement("attribs");
        
        for (TableItem item : attributes.getItens())
            if (item.isSelected())
                attributes.remove(item);
    }
}
