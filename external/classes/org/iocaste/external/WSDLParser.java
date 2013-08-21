package org.iocaste.external;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMElement;

public class WSDLParser {
    
    public static final Service get(String urlwsdl) throws Exception {
        List<ElementDetail> details;
        Map<String, OpMessage> messages;
        Map<String, PortType> porttypes;
        Map<String, Binding> bindings;
        URL url = new URL(urlwsdl);
        ServiceData servicedata = new ServiceData();
        Map<String, List<ElementDetail>> values = new HashMap<>();
        OMElement result = WSClient.getWSDLObject(url.openStream());
        
        Context.convertXmlToMap(values, result, null);
        details = values.get("definitions");
        servicedata.namespace = details.get(0).attributes.
                get("targetNamespace");
        
        details = values.get("definitions.types");
        servicedata.types = getTypes(details);
        
        details = values.get("definitions.message");
        messages = getOpMessages(details, servicedata.types);
        
        details = values.get("definitions.portType");
        porttypes = getPortTypes(details, messages);
        
        details = values.get("definitions.binding");
        bindings = getBindings(details, porttypes);
        
        details = values.get("definitions.service");
        servicedata.name = details.get(0).attributes.get("name");
        servicedata.ports = getPorts(details, bindings);
        
        return new Service(servicedata);
    }
    
    private static final Map<String, Binding> getBindings(
            List<ElementDetail> details, Map<String, PortType> porttypes) {
        Binding binding;
        String operationname, porttypename;
        Map<String, Binding> bindings_ = new HashMap<>();
        
        for (ElementDetail ebinding : details) {
            binding = new Binding();
            binding.name = ebinding.attributes.get("name");
            porttypename = Context.extractValue(ebinding, "type");
            for (ElementDetail eoperation : ebinding.children) {
                operationname = eoperation.attributes.get("name");
                binding.porttypes.put(operationname,
                        porttypes.get(porttypename));
            }
            bindings_.put(binding.name, binding);
        }
        
        return bindings_;
    }
    
    private static final Type getComplexType(ElementDetail element,
            String name) {
        TypeData type = null, ctype;
        
        if (element.children.size() == 0)
            return null;

        for (ElementDetail sequence : element.children) {
            if (!sequence.name.endsWith(".complexType.sequence"))
                continue;
            
            for (ElementDetail eelement : sequence.children) {
                if (type == null) {
                    type = new TypeData();
                    type.name = element.attributes.get("name");
                }
                
                if (type.name == null) {
                    type.name = eelement.attributes.get("name");
                    type.min = eelement.attributes.get("minOccurs");
                    type.max = eelement.attributes.get("maxOccurs");
                    type.ctype = Context.extractValue(eelement, "type");
                } else {
                    ctype = new TypeData();
                    ctype.name = eelement.attributes.get("name");
                    ctype.min = eelement.attributes.get("minOccurs");
                    ctype.max = eelement.attributes.get("maxOccurs");
                    ctype.ctype = Context.extractValue(eelement, "type");
                    type.fields.put(ctype.name, new Type(ctype));
                }
                
                type.aname = new StringBuilder(name).
                        append(".").append(type.name).toString();
            }
        }
        
        return (type == null)? null : new Type(type);
    }
    
    private static final Map<String, OpMessage> getOpMessages(
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
                partelement = Context.extractValue(part, "element");
                if (partelement == null)
                    partelement = Context.extractValue(part, "type");
                
                type = types.get(partelement);
                if (type == null)
                    continue;
                
                opmessage.parameters.add(type);
            }
        }
        
        return messages_;
    }
    
    private static final Map<String, Port> getPorts(
            List<ElementDetail> services, Map<String, Binding> bindings) {
        Port port;
        String bindingname;
        Binding binding;
        Map<String, Port> ports = new HashMap<>();
        
        for (ElementDetail service : services) {
            for (ElementDetail eport : service.children) {
                if (!eport.name.equals("definitions.service.port"))
                    continue;
                
                bindingname = Context.extractValue(eport, "binding");
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
    
    private static final Map<String, PortType> getPortTypes(
            List<ElementDetail> porttypes, Map<String, OpMessage> messages) {
        String messagename, opname;
        OperationData operation;
        PortType porttype;
        List<Type> types;
        Map<String, PortType> porttypes_ = new HashMap<>();
        
        for (ElementDetail eporttype : porttypes) {
            porttype = new PortType();
            porttype.name = eporttype.attributes.get("name");
            porttypes_.put(porttype.name, porttype);
            for (ElementDetail eoperation : eporttype.children) {
                opname = eoperation.attributes.get("name");
                operation = new OperationData();
                operation.name = opname;
                porttype.operations.put(opname, new Operation(operation));
                for (ElementDetail eopitem : eoperation.children) {
                    messagename = Context.extractValue(eopitem, "message");
                    if (messagename == null)
                        continue;
                    
                    types = messages.get(messagename).parameters;
                    if (types == null)
                        continue;
                    
                    switch (eopitem.name) {
                    case "definitions.portType.operation.input":
                        for (Type type : types)
                            operation.input.put(type.getName(), type);
                        break;
                    case "definitions.portType.operation.output":
                        for (Type type : types)
                            operation.output.put(type.getName(), type);
                        break;
                    }
                }
            }
        }
        
        return porttypes_;
    }
    
    private static final Type getPrimitive(String name) {
        TypeData type = new TypeData();
        
        type.name = name;
        
        return new Type(type);
    }
    
    private static final Map<String, Type> getTypes(List<ElementDetail> types) {
        Type type;
        String name;
        Map<String, Type> types_ = new HashMap<>();
        
        types_.put("string", getPrimitive("string"));
        types_.put("short", getPrimitive("short"));
        types_.put("string", getPrimitive("int"));
        types_.put("short", getPrimitive("float"));
        
        for (ElementDetail etype : types)
            for (ElementDetail eschema : etype.children)
                for (ElementDetail eelement : eschema.children) {
                    type = null;
                    name = eelement.attributes.get("name");
                    switch (eelement.name) {
                    case "definitions.types.schema.element":
                        for (ElementDetail ctype : eelement.children)
                            type = getComplexType(ctype, name);
                        break;
                    case "definitions.types.schema.complexType":
                        type = getComplexType(eelement, name);
                        break;
                    }
                    
                    if (type == null)
                        continue;
                    types_.put(eelement.attributes.get("name"), type);
                }
        
        return types_;
    }
}
