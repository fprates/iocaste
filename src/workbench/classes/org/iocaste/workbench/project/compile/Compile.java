package org.iocaste.workbench.project.compile;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryInstance;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class Compile extends AbstractCommand {
    private CompileData data;

    public Compile() {
        optional("project");
        data = new CompileData();
    }
  
    private final XMLElement createWebXML(CompileData data) {
        XMLElement welcome, welcomefile;
        XMLElement webapp = new XMLElement("web-app");
        
        webapp.head("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        webapp.add("id", data.project);
        webapp.add("xmlns", "http://java.sun.com/xml/ns/javaee");
        webapp.add("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        webapp.add("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee "
                + "http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd");
        webapp.add("version", "2.5");
          
        if (data.serviceclass != null)
            createWebXMLServlet(webapp,
                    "iocaste_server",
                    data.serviceclass,
                    "/services.html", null, null);
        
        if (data.entryclass != null)
            createWebXMLServlet(webapp,
                    "iocaste_servlet",
                    "org.iocaste.shell.common.IocasteServlet",
                    "/view.html", "form", data.entryclass);
        
        welcome = new XMLElement("welcome-file-list");
        welcomefile = new XMLElement("welcome-file");
        welcomefile.addInner("index.html");
        welcome.addChild(welcomefile);
        webapp.addChild(welcome);
        
        return webapp;
    }
    
    private final void createWebXMLServlet(XMLElement webapp,
            String name, String classname, String url, String param,
            String value) {
        XMLElement urlpattern, initparam, paramname, paramvalue;
        XMLElement servlet = new XMLElement("servlet");
        XMLElement servletname = new XMLElement("servlet-name");
        XMLElement servletclass = new XMLElement("servlet-class");
        XMLElement servletmapping = new XMLElement("servlet-mapping");
        
        servletname.addInner(name);
        servlet.addChild(servletname);
        
        servletclass.addInner(classname);
        servlet.addChild(servletclass);
        webapp.addChild(servlet);
  
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
        webapp.addChild(servletmapping);
    }
    
    private final XMLElement createInstall(CompileData data) {
        ExtendedObject[] objects;
        XMLElement link, linkname, linkprogram, profile;
        XMLElement install = new XMLElement("install");
        XMLElement links = new XMLElement("links");

        install.head("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        profile = new XMLElement("profile");
        profile.addInner("DEVELOP");
        install.addChild(profile);
        
        objects = data.extcontext.project.getItems("link");
        if (objects != null) {
            install.addChild(links);
            for (ExtendedObject object : objects) {
                link = new XMLElement("link");
                linkname = new XMLElement("name");
                linkname.addInner(object.getst("NAME"));
                link.addChild(linkname);
                linkprogram = new XMLElement("program");
                linkprogram.addInner(object.getst("COMMAND"));
                link.addChild(linkprogram);
                links.addChild(link);
            }
        }
            
        return install;
    }
    
    private final void deployApplication(CompileData data)
            throws Exception {
        Iocaste iocaste;
        Directory war;
        DirectoryInstance file;
        XMLElement webapp, install;
        
        data.entryclass =
                "org.iocaste.workbench.common.engine.ApplicationEngine";
        webapp = createWebXML(data);
        install = createInstall(data);
        
        war = new Directory(data.project.concat(".war"));
        war.addDir("META-INF");
        war.addDir("WEB-INF", "classes");
        war.addDir("WEB-INF", "lib");
        
        file = war.file("WEB-INF", "web.xml");
        file.content(webapp.toString());
        
        file = war.file("META-INF", "install.txt");
        file.content(install.toString());
        
        file = war.copy("WEB-INF", "lib", "iocaste-workbench.jar");
        file.source("WORKBENCH_LIBS", "iocaste-workbench.jar");
        file = war.copy("WEB-INF", "lib", "iocaste.jar");
        file.source("WORKBENCH_LIBS", "iocaste.jar");
        
        iocaste = new Iocaste(data.context.function);
        iocaste.write("WEBAPPS", war, Iocaste.JAR);
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ComplexDocument document;

        data.extcontext = getExtendedContext();
        data.project = parameters.get("project");
        if ((data.project == null) && (data.extcontext.project == null)) {
            message(Const.ERROR, "undefined.project");
            return;
        }
        
        if (data.project == null)
            data.project = data.extcontext.project.getstKey();
        
        document = getDocument("project", data.project);
        if (document == null) {
            message(Const.ERROR, "invalid.project");
            return;
        }
        
        data.extcontext = getExtendedContext();
        data.context = context;
        deployApplication(data);
        message(Const.STATUS, "project.compiled");
    }

}

class CompileData {
    public String project, serviceclass, entryclass;
    public Context extcontext;
    public PageBuilderContext context;
}