package org.iocaste.workbench.project.compile;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.utils.XMLElement;

public class WebConfigFile extends AbstractConfigFile {
    
    public WebConfigFile(PageBuilderContext context) {
        super(context, "web-app");
        directory = "WEB-INF";
        file = "web.xml";
    }

    private final void createWebXMLServlet(String name, String classname,
            String url, String param, String value) {
        XMLElement urlpattern, initparam, paramname, paramvalue;
        XMLElement servlet = new XMLElement("servlet");
        XMLElement servletname = new XMLElement("servlet-name");
        XMLElement servletclass = new XMLElement("servlet-class");
        XMLElement servletmapping = new XMLElement("servlet-mapping");
        
        servletname.addInner(name);
        servlet.addChild(servletname);
        
        servletclass.addInner(classname);
        servlet.addChild(servletclass);
        root.addChild(servlet);
  
        urlpattern = new XMLElement("url-pattern");
        urlpattern.addInner(url);
        
        servletmapping.addChild(servletname);
        servletmapping.addChild(urlpattern);
       
        if (param == null)
            return;
      
        initparam = new XMLElement("init-param");
        paramname = new XMLElement("param-name");
        paramname.addInner(param);
        paramvalue = new XMLElement("param-value");
        paramvalue.addInner(value);
        initparam.addChild(paramname);
        initparam.addChild(paramvalue);
        servlet.addChild(initparam);
        root.addChild(servletmapping);
    }

    @Override
    public void run(CompileData data) {
        XMLElement welcome, welcomefile;
        
        root.add("id", data.project);
        root.add("xmlns", "http://java.sun.com/xml/ns/javaee");
        root.add("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.add("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee "
                + "http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd");
        root.add("version", "2.5");
          
        if (data.serviceclass != null)
            createWebXMLServlet("iocaste_server", data.serviceclass,
                    "/services.html", null, null);
        
        if (data.entryclass != null)
            createWebXMLServlet("iocaste_servlet",
                    "org.iocaste.shell.common.IocasteServlet",
                    "/view.html", "form", data.entryclass);
        
        welcome = new XMLElement("welcome-file-list");
        welcomefile = new XMLElement("welcome-file");
        welcomefile.addInner("index.html");
        welcome.addChild(welcomefile);
        root.addChild(welcome);
    }

}
