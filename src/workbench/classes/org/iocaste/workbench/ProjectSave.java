package org.iocaste.workbench;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.AbstractPage;

public class ProjectSave extends AbstractActionHandler {
    private static final String AUTOMATED_ENTRY =
            "org.iocaste.workbench.common.engine.ApplicationEngine";
    private static final int LINK= 200;
    
    private void add(StringBuilder content, StringBuilder level, int type,
            String... args) {

        content.append(level).append(":").append(type);
        if (args != null)
            for (String arg : args)
                content.append(":").append(arg);
        
        content.append("\n");
    }
    
    private void additem(
            StringBuilder content, StringBuilder parent, ProjectTreeItem item) {
        StringBuilder level;
        
        level = marklevel(parent, item.name);
        add(content, level, item.type);
        for (ProjectTreeItem child : item.items)
            additem(content, level, child);
    }
    
    private final void addJarItems(JarOutputStream jar, String path,
            String base) throws Exception {
        String jaritem;
        FileChannel channel;
        int limit;
        ByteBuffer buffer = null;
        
        for (File file : new File(path).listFiles()) {
            jaritem = file.getPath().substring(base.length());
            if (file.isDirectory()) {
                jar.putNextEntry(new JarEntry(jaritem.concat(File.separator)));
                addJarItems(jar, file.getPath(), base);
                continue;
            }
            
            jar.putNextEntry(new JarEntry(jaritem));
            channel = new FileInputStream(file).getChannel();
            
            if (buffer == null)
                buffer = ByteBuffer.allocate(64*1024);
            
            buffer.rewind();
            while ((limit = channel.read(buffer)) > 0) {
                buffer.flip();
                if (buffer.hasArray())
                    jar.write(buffer.array(), 0, limit);
                buffer.clear();
            }
            
            channel.close();
            jar.closeEntry();
        }
    }
    
    private final void buildAppSpec(Context extcontext, CompileData data) {
        ExtendedObject[] links;
        StringBuilder content, projectlevel, viewlevel, level;
        ProjectView project;
        
        content = new StringBuilder();
        projectlevel = marklevel(null, extcontext.project);
        add(content, projectlevel, -1);
        
        for (String view : extcontext.views.keySet()) {
            viewlevel = marklevel(projectlevel, view);
            add(content, viewlevel, ViewSpecItem.TYPES.VIEW.ordinal());
            project = extcontext.views.get(view);
            for (ProjectTreeItem item : project.treeitems.get(view).items)
                additem(content, viewlevel, item);
        }
        
        projectlevel = viewlevel = null;
        links = tableitemsget("links");
        if (links != null) {
            for (ExtendedObject link : links) {
                if (Documents.isInitial(link))
                    continue;
                level = marklevel(null, link.getst("NAME"));
                add(content, level, LINK, link.getst("COMMAND"));
            }
        }
        data.views = content.toString();
    }
    
    private final String compileProject(Context extcontext, CompileData data)
            throws Exception {
//        List<File> files;
//        String message;
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        
//        if (compiler == null)
//            return "compiler.unavailable";
//         
//        files = new ArrayList<>();
//        for (String sourcefile : data.sourcefiles)
//            files.add(new File(sourcefile));
//        
//        message = compileSources(files, compiler, data);
//        if (message != null)
//            return message;
//        
        copyLibraries(extcontext, data);
        createWebXML(extcontext, data);
        return null;
    }
    
    private final void copyFile(File to, File from) throws Exception {
        FileInputStream is = new FileInputStream(from);
        FileChannel fcfrom = is.getChannel();
        FileOutputStream os = new FileOutputStream(to);
        FileChannel fcto = os.getChannel();
        
        to.createNewFile();
        fcto.transferFrom(fcfrom, 0, fcfrom.size());
        fcfrom.close();
        fcto.close();
        os.close();
        is.close();
    }
    
    private final void copyLibraries(Context extcontext, CompileData data)
            throws Exception {
        String libfrom = Common.composeFileName(
                data.workbenchpath, "WEB-INF", "lib");
        String libto = Common.composeFileName(
                extcontext.projectdir, "bin", "WEB-INF", "lib");
      
        new File(libto).mkdir();
        for (File file : new File(libfrom).listFiles())
            copyFile(new File(Common.composeFileName(
                    libto, file.getName())), file);
    }
    
    private final void createProjectFiles(Context extcontext, CompileData data)
            throws Exception {
        String[] tokens;
        OutputStream os;
        File file;
        String dir, code, bindir;
        long packageid, sourceid;
        ExtendedObject source;
        Map<Long, String> sources;
//        TextEditorTool tetool = new TextEditorTool(data.context);
        
        file = new File(extcontext.repository);
        if (!file.exists())
            file.mkdir();
        
        removeCompleteDir(extcontext.projectdir);
        createViewsSpecFile(extcontext, data);
        
        bindir = Common.composeFileName(extcontext.projectdir,
                "bin", "WEB-INF", "classes");
        new File(bindir).mkdirs();
        
//        for (ExtendedObject package_ : data.packages) {
//            dir = package_.getst("PACKAGE_NAME");
//            dir = dir.replaceAll("[\\.]", File.separator);
//            dir = Common.composeFileName(data.context.projectdir, "src", dir);
//            new File(dir).mkdirs();
//                
//            packageid = package_.getl("PACKAGE_ID");
//            data.context.projectsources = Common.getSources(
//                    packageid, data.context);
//            for (String sourcename : data.context.projectsources.keySet()) {
//                source = data.context.projectsources.get(sourcename);
//                if (packageid != source.getl("PACKAGE_ID"))
//                    continue;
//                
//                sourceid = source.getl("SOURCE_ID");
//                sources = tetool.get(data.context.projectsourceobj, sourceid);
//                code = sources.get(sourceid);
//                
//                tokens = sourcename.split("\\.");
//                sourcename = tokens[tokens.length - 1];
//                sourcename = Common.composeFileName(dir, sourcename);
//                sourcename = sourcename.concat(".java");
//                data.sourcefiles.add(sourcename);
//                
//                file = new File(sourcename);
//                file.createNewFile();
//                os = new FileOutputStream(file, false);
//                os.write(code.getBytes());
//                os.flush();
//                os.close();
//            }
//        }
    }
    
    private final void createViewsSpecFile(Context extcontext, CompileData data)
            throws Exception {
        String path;
        File file;
        OutputStream os;
        
        path = Common.composeFileName(extcontext.projectdir, "bin", "META-INF");
        if (!new File(path).mkdirs())
            throw new IocasteException("directory creation error.");
        path = Common.composeFileName(path, "context.txt");
        
        file = new File(path);
        file.createNewFile();
        os = new FileOutputStream(file, false);
        os.write(data.views.getBytes());
        os.flush();
        os.close();
    }
    
    private final void createWebXML(Context extcontext, CompileData data)
            throws Exception {
        File file;
        OutputStream os;
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        XMLElement welcome, welcomefile;
        XMLElement webapp = new XMLElement("web-app");
          
        webapp.add("id", extcontext.project);
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
        
        xml = new StringBuilder(xml).append(System.lineSeparator()).
                append(webapp.toString()).toString();
        file = new File(Common.composeFileName(
                extcontext.projectdir, "bin", "WEB-INF", "web.xml"));
        os = new FileOutputStream(file);
        
        os.write(xml.getBytes());
        os.flush();
        os.close();
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
    
    private final void deployApplication(Context extcontext, CompileData data)
            throws Exception {
        String jarname = extcontext.project;
        String dest = Common.composeFileName(
                System.getProperty("catalina.home"),
                "webapps",
                jarname.concat(".war"));
        OutputStream os = new FileOutputStream(dest);
        JarOutputStream jar = new JarOutputStream(os);
        String bindir = Common.composeFileName(extcontext.projectdir, "bin");
        
        addJarItems(jar, bindir, bindir);
        jar.close();
        os.close();
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        CompileData compiledata = new CompileData();
        
        compiledata.workbenchpath = ((AbstractPage)context.function).
                getRealPath();
        compiledata.entryclass = AUTOMATED_ENTRY;
        buildAppSpec(extcontext, compiledata);
        createProjectFiles(extcontext, compiledata);
        compileProject(extcontext, compiledata);
        deployApplication(extcontext, compiledata);
    }
    
    private StringBuilder marklevel(StringBuilder level, String name) {
        StringBuilder result = new StringBuilder();
        
        if (level != null)
            result.append(level).append(".");
        
        return result.append(name);
    }
    
    private final void removeCompleteDir(String dir) {
        File origin = new File(dir);
        File[] files = origin.listFiles();
        
        if (files != null)
            for (File file : files) {
                if (file.isDirectory())
                    removeCompleteDir(file.getAbsolutePath());
                file.delete();
            }
        
        origin.delete();
    }

}

class CompileData {
    public String views;
    public ExtendedObject[] packages;
    public Context context;
    public List<String> sourcefiles;
    public String entryclass, serviceclass, workbenchpath;
    
    public CompileData() {
        sourcefiles = new ArrayList<>();
    }
}
