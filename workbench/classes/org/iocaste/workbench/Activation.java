package org.iocaste.workbench;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Activation {
    private static final void addJarItems(JarOutputStream jar, String path,
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
    
    private static final void compileProject(Context context) throws Exception {
        ProjectPackage package_;
        Source source;
        List<File> files;
        JavaCompiler compiler;
        String message;
        InputComponent input;
//      
//      if (!project.created) {
//          view.message(Const.ERROR, "project.not.created");
//          return;
//      }
//
        compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            context.view.message(Const.ERROR, "compiler.unavailable");
            return;
        }
        
        files = new ArrayList<>();
        for (String packagename : context.project.packages.keySet()) {
            package_ = context.project.packages.get(packagename);
            for (String sourcename : package_.sources.keySet()) {
                source = package_.sources.get(sourcename);
                files.add(new File(source.filename));
            }
        }

        input = context.view.getElement("output");
        message = compileSources(files, compiler, context);
        if (message != null) {
            input.set(message);
            context.view.message(Const.ERROR, "compiling.error");
            return;
        }
        
        copyLibraries(context);
        createWebXML(context);
        
        input.set(null);
        context.view.message(Const.STATUS, "successful.compiling");
    }
    
    private static final String compileSources(List<File> files,
            JavaCompiler compiler, Context context) throws Exception {
        CompilationTask task;
        Writer writer;
        List<String> options;
        StringBuilder cp;
        StandardJavaFileManager fmngr;
        Iterable<? extends JavaFileObject> cunits;
        String prefix;
        File file;
        
        fmngr = compiler.getStandardFileManager(
                null, context.view.getLocale(), null);
        
        cunits = fmngr.getJavaFileObjects(files.toArray(new File[0])); 
        prefix = composeFileName(context.path, "WEB-INF", "lib", "");
        
        file = new File(prefix);
        cp = new StringBuilder();
        for (String filename : file.list(new JarFilter())) {
            if (cp.length() > 0)
                cp.append(":");
        
            cp.append(prefix).append(filename);
        }
        
        options = new ArrayList<>();
        options.addAll(Arrays.asList("-cp", cp.toString()));
        options.addAll(Arrays.asList("-d", composeFileName(
                context.project.dir, "bin", "WEB-INF", "classes")));
        
        writer = new StringWriter();
        task = compiler.getTask(writer, fmngr, null, options, null, cunits);
        prefix = (task.call())? null : writer.toString();
        writer.close();
        fmngr.close();
        
        return prefix;
    }
    
    private static final String composeFileName(String... names) {
        StringBuilder sb = new StringBuilder();
        
        for (String name : names) {
            if (sb.length() > 0)
                sb.append(File.separator);
        
            sb.append(name);
        }
        
        return sb.toString();
    }
    
    private static final void copyFile(File to, File from) throws Exception {
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
    
    private static final void copyLibraries(Context context) throws Exception {
        String libfrom = composeFileName(context.path, "WEB-INF", "lib");
        String libto = composeFileName(
                context.project.dir, "bin", "WEB-INF", "lib");
        
        new File(libto).mkdir();
        for (File file : new File(libfrom).listFiles())
            copyFile(new File(composeFileName(libto, file.getName())), file);
    }
    
    private static final void createProjectFiles(Context context)
            throws IOException {
        OutputStream os;
        File file;
        String dir;
        ProjectPackage package_;
        Source source;
        StringBuilder bindir;
        DataForm form = context.view.getElement("project");
        String projectname = form.get("NAME").get();
        
        if (context.project.dir == null)
            context.project.dir = composeFileName(
                    context.repository, projectname);
        
        removeCompleteDir(context.project.dir);
        new File(context.project.dir).mkdir();
        bindir = new StringBuilder(context.project.dir);
        for (String dirname : new String[] {"bin", "WEB-INF", "classes"})
            new File(bindir.append(File.separator).
                    append(dirname).toString()).mkdir();
        
        for (String packagename : context.project.packages.keySet()) {
            dir = packagename.replaceAll("[\\.]", File.separator);
            dir = composeFileName(context.project.dir, "src", dir);
            new File(dir).mkdirs();
            
            package_ = context.project.packages.get(packagename);
            for (String sourcename : package_.sources.keySet()) {
                source = package_.sources.get(sourcename);                
                source.filename = composeFileName(dir, sourcename);
                
                file = new File(source.filename);
                file.createNewFile();
                os = new FileOutputStream(file, false);
                os.write(source.code.getBytes());
                os.flush();
                os.close();
            }
        }
    }
    
    private static final void createWebXML(Context context) throws Exception {
        File file;
        OutputStream os;
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        XMLElement welcome, welcomefile;
        XMLElement webapp = new XMLElement("web-app");
        
        webapp.add("id", (String)context.project.header.getValue("NAME"));
        webapp.add("xmlns", "http://java.sun.com/xml/ns/javaee");
        webapp.add("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        webapp.add("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee "
                + "http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd");
        webapp.add("version", "2.5");
//        
//            <description>Programming Workbench</description>
//            <display-name>Iocaste Programming Workbench</display-name>
        
        if (context.project.service != null)
            createWebXMLServlet(webapp,
                    "iocaste_server",
                    context.project.service,
                    "/services.html", null, null);
        
        if (context.project.entryclass != null)
            createWebXMLServlet(webapp,
                    "iocaste_servlet",
                    "org.iocaste.shell.common.IocasteServlet",
                    "/view.html", "form", context.project.entryclass);
        
        welcome = new XMLElement("welcome-file-list");
        welcomefile = new XMLElement("welcome-file");
        welcomefile.addInner("index.html");
        welcome.addChild(welcomefile);
        webapp.addChild(welcome);
        
        xml = new StringBuilder(xml).append(System.lineSeparator()).
                append(webapp.toString()).toString();
        file = new File(composeFileName(
                context.project.dir, "bin", "WEB-INF", "web.xml"));
        os = new FileOutputStream(file);
        
        os.write(xml.getBytes());
        os.flush();
        os.close();
    }
    
    private static final void createWebXMLServlet(XMLElement webapp,
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
    
    private static final void deployApplication(Context context)
            throws Exception {
        String jarname = context.project.header.getValue("NAME");
        String dest = composeFileName(System.getProperty("catalina.home"),
                "webapps", jarname.concat(".war"));
        OutputStream os = new FileOutputStream(dest);
        JarOutputStream jar = new JarOutputStream(os);
        String bindir = composeFileName(context.project.dir, "bin");
        
        addJarItems(jar, bindir, bindir);
        jar.close();
        os.close();
    }

    private static final void removeCompleteDir(String dir) {
        for (File file : new File(dir).listFiles()) {
            if (file.isDirectory())
                removeCompleteDir(file.getAbsolutePath());
            file.delete();
        }
        
        new File(dir).delete();
    }
    
    public static final void start(Context context) throws Exception {
        updateCurrentSource(context);
        createProjectFiles(context);
        compileProject(context);
        deployApplication(context);
    }
    
    private static final void updateCurrentSource(Context context) {
        ExtendedObject header = context.project.header;
        InputComponent input = context.view.getElement("editor");
        ProjectPackage package_ = context.project.packages.get(
                header.getValue("PACKAGE"));
        Source source = package_.sources.get(header.getValue("CLASS"));
        
        source.code = input.get(); 
    }
}

class JarFilter implements FilenameFilter {

    @Override
    public boolean accept(File arg0, String name) {
        return name.toLowerCase().endsWith(".jar");
    }
    
}