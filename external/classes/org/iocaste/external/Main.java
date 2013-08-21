package org.iocaste.external;

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
        DataForm selection = view.getElement("selection");
        String wsdl = selection.get("wsdl").get();
        
        context.service = WSDLParser.get(wsdl);
        view.redirect(null, "output");
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
    
    private final void printParameter(String parameter, View view,
            Service service, String ident) {
        StringBuilder sb = new StringBuilder(ident).append(parameter);
        Type type = service.getType(parameter);
        
        if (type == null) {
            view.print(sb.toString());
            return;
        }
        
        view.print(sb.append(": ").append(type.getValueType()).toString());
        for (String fieldname : type.getFieldsKeys())
            printParameter(fieldname, view, service, ident+"-");
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
        Service service = context.service;
        
        pagecontrol.add("back");
        for (String portname : service.getPortsKeys()) {
            view.print(portname);
            port = service.getPort(portname);
            for (String operationname : port.operations.keySet()) {
                view.print("-- ".concat(operationname));
                operation = port.operations.get(operationname);
                
                for (String parameter : operation.getInputKeys())
                    printParameter(parameter, view, service, "---");
                
                for (String parameter : operation.getOutputKeys())
                    printParameter(parameter, view, service, "---");
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
    
    public final void external(View view) throws Exception {
//        DocumentModel model;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        CallData calldata = new CallData();
        
        pagecontrol.add("home");

        calldata.url = "http://localhost:8080/axis2/services/Ping";
        calldata.service = WSDLParser.
                get(calldata.url.concat("?wsdl"));
        
        calldata.function = "ping";
        calldata.port = "PingHttpEndpoint";
//        model = new DocumentModel("EXTERNAL_OBJECT");
//        model.add(new DocumentModelItem("message"));
//        
//        calldata.parameter = new ExtendedObject(model);
//        calldata.parameter.setValue("message", "teste");
        
        WSClient.call(calldata);
    }
}
