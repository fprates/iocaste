package org.iocaste.workbench.compiler;

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
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.Common;

public class Compile {
//    private static final void addJarItems(JarOutputStream jar, String path,
//            String base) throws Exception {
//        String jaritem;
//        FileChannel channel;
//        int limit;
//        ByteBuffer buffer = null;
//        
//        for (File file : new File(path).listFiles()) {
//            jaritem = file.getPath().substring(base.length());
//            if (file.isDirectory()) {
//                jar.putNextEntry(new JarEntry(jaritem.concat(File.separator)));
//                addJarItems(jar, file.getPath(), base);
//                continue;
//            }
//            
//            jar.putNextEntry(new JarEntry(jaritem));
//            channel = new FileInputStream(file).getChannel();
//            
//            if (buffer == null)
//                buffer = ByteBuffer.allocate(64*1024);
//            
//            buffer.rewind();
//            while ((limit = channel.read(buffer)) > 0) {
//                buffer.flip();
//                if (buffer.hasArray())
//                    jar.write(buffer.array(), 0, limit);
//                buffer.clear();
//            }
//            
//            channel.close();
//            jar.closeEntry();
//        }
//    }
//    
//    private static final String compileProject(CompileData data)
//            throws Exception {
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
//        copyLibraries(data);
//        createWebXML(data);
//        return null;
//    }
//
//    private static final String compileSources(List<File> files,
//            JavaCompiler compiler, CompileData data) throws Exception {
//        CompilationTask task;
//        Writer writer;
//        List<String> options;
//        StringBuilder cp;
//        StandardJavaFileManager fmngr;
//        Iterable<? extends JavaFileObject> cunits;
//        String prefix;
//        File file;
//        
//        fmngr = compiler.getStandardFileManager(
//                null, data.context.view.getLocale(), null);
//      
//        cunits = fmngr.getJavaFileObjects(files.toArray(new File[0]));
//        prefix = Common.composeFileName(
//                data.workbenchpath, "WEB-INF", "lib", "");
//        
//        file = new File(prefix);
//        cp = new StringBuilder();
//        for (String filename : file.list(new JarFilter())) {
//            if (cp.length() > 0)
//                cp.append(":");
//        
//            cp.append(prefix).append(filename);
//        }
//      
//        options = new ArrayList<>();
//        options.addAll(Arrays.asList("-cp", cp.toString()));
//        options.addAll(Arrays.asList("-d", Common.composeFileName(
//              data.context.projectdir, "bin", "WEB-INF", "classes")));
//      
//        writer = new StringWriter();
//        task = compiler.getTask(writer, fmngr, null, options, null, cunits);
//        prefix = (task.call())? null : writer.toString();
//        writer.flush();
//        writer.close();
//        fmngr.close();
//      
//        return prefix;
//    }
//    
//    private static final void copyFile(File to, File from) throws Exception {
//        FileInputStream is = new FileInputStream(from);
//        FileChannel fcfrom = is.getChannel();
//        FileOutputStream os = new FileOutputStream(to);
//        FileChannel fcto = os.getChannel();
//        
//        to.createNewFile();
//        fcto.transferFrom(fcfrom, 0, fcfrom.size());
//        fcfrom.close();
//        fcto.close();
//        os.close();
//        is.close();
//    }
//    
//    private static final void copyLibraries(CompileData data) throws Exception {
//        String libfrom = Common.composeFileName(
//                data.workbenchpath, "WEB-INF", "lib");
//        String libto = Common.composeFileName(
//                data.context.projectdir, "bin", "WEB-INF", "lib");
//      
//        new File(libto).mkdir();
//        for (File file : new File(libfrom).listFiles())
//            copyFile(new File(Common.composeFileName(
//                    libto, file.getName())), file);
//    }
//    
//    private static final void createProjectFiles(CompileData data)
//            throws IOException {
//        String[] tokens;
//        OutputStream os;
//        File file;
//        String dir, code, bindir;
//        long packageid, sourceid;
//        ExtendedObject source;
//        Map<Long, String> sources;
//        TextEditorTool tetool = new TextEditorTool(data.context);
//        
//        file = new File(data.context.repository);
//        if (!file.exists())
//            file.mkdir();
//        
//        removeCompleteDir(data.context.projectdir);
//        new File(data.context.projectdir).mkdir();
//        bindir = Common.composeFileName(data.context.projectdir,
//                "bin", "WEB-INF", "classes");
//        new File(bindir).mkdirs();
//        
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
//    }
//  
//    private static final void createWebXML(CompileData data) throws Exception {
//        File file;
//        OutputStream os;
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
//        XMLElement welcome, welcomefile;
//        XMLElement webapp = new XMLElement("web-app");
//          
//        webapp.add("id", data.context.projectname);
//        webapp.add("xmlns", "http://java.sun.com/xml/ns/javaee");
//        webapp.add("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        webapp.add("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee "
//                + "http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd");
//        webapp.add("version", "2.5");
//          
//        if (data.serviceclass != null)
//            createWebXMLServlet(webapp,
//                    "iocaste_server",
//                    data.serviceclass,
//                    "/services.html", null, null);
//        
//        if (data.entryclass != null)
//            createWebXMLServlet(webapp,
//                    "iocaste_servlet",
//                    "org.iocaste.shell.common.IocasteServlet",
//                    "/view.html", "form", data.entryclass);
//        
//        welcome = new XMLElement("welcome-file-list");
//        welcomefile = new XMLElement("welcome-file");
//        welcomefile.addInner("index.html");
//        welcome.addChild(welcomefile);
//        webapp.addChild(welcome);
//        
//        xml = new StringBuilder(xml).append(System.lineSeparator()).
//                append(webapp.toString()).toString();
//        file = new File(Common.composeFileName(
//                data.context.projectdir, "bin", "WEB-INF", "web.xml"));
//        os = new FileOutputStream(file);
//        
//        os.write(xml.getBytes());
//        os.flush();
//        os.close();
//    }
//  
//    private static final void createWebXMLServlet(XMLElement webapp,
//            String name, String classname, String url, String param,
//            String value) {
//        XMLElement urlpattern, initparam, paramname, paramvalue;
//        XMLElement servlet = new XMLElement("servlet");
//        XMLElement servletname = new XMLElement("servlet-name");
//        XMLElement servletclass = new XMLElement("servlet-class");
//        XMLElement servletmapping = new XMLElement("servlet-mapping");
//        
//        servletname.addInner(name);
//        servlet.addChild(servletname);
//        
//        servletclass.addInner(classname);
//        servlet.addChild(servletclass);
//        webapp.addChild(servlet);
//  
//        urlpattern = new XMLElement("url-pattern");
//        urlpattern.addInner(url);
//        
//        servletmapping.addChild(servletname);
//        servletmapping.addChild(urlpattern);
//       
//        if (param == null)
//            return;
//      
//        initparam = new XMLElement("init-param");
//        paramname = new XMLElement("param-name");
//        paramname.addInner(param);
//        paramvalue = new XMLElement("param-value");
//        paramvalue.addInner(value);
//        initparam.addChild(paramname);
//        initparam.addChild(paramvalue);
//        servlet.addChild(initparam);
//        webapp.addChild(servletmapping);
//    }
//
//  
//    private static final void deployApplication(CompileData data)
//            throws Exception {
//        String jarname = data.context.projectname;
//        String dest = Common.composeFileName(
//                System.getProperty("catalina.home"),
//                "webapps",
//                jarname.concat(".war"));
//        OutputStream os = new FileOutputStream(dest);
//        JarOutputStream jar = new JarOutputStream(os);
//        String bindir = Common.composeFileName(data.context.projectdir, "bin");
//        
//        addJarItems(jar, bindir, bindir);
//        jar.close();
//        os.close();
//    }
//    
//    public static final String execute(String project, Context context)
//            throws Exception {
//        CompileData data;
//        ExtendedObject object;
//        String error;
//        
//        object = Common.getProject(project, context);
//        if (object == null)
//            return "invalid.project";
//
//        data = new CompileData();
//        data.packages = Common.getPackages(project, context);
//        if (data.packages == null)
//            return "project.has.no.packages";
//        
//        data.context = context;
//        data.entryclass = object.get("ENTRY_CLASS");
//        data.workbenchpath = ((AbstractPage)data.context.function).
//                getRealPath("");
//        context.projectname = project;
//        context.projectsourceobj = object.get("SOURCE_OBJ");
//        
//        createProjectFiles(data);
//        error = compileProject(data);
//        if (error != null)
//            return error;
//        
//        deployApplication(data);
//        return "project.compiled";
//    }
//    
//    private static final void removeCompleteDir(String dir) {
//        File origin = new File(dir);
//        File[] files = origin.listFiles();
//        
//        if (files != null)
//            for (File file : files) {
//                if (file.isDirectory())
//                    removeCompleteDir(file.getAbsolutePath());
//                file.delete();
//            }
//        
//        origin.delete();
//    }
//}
//
//class CompileData {
//    public ExtendedObject[] packages;
//    public Context context;
//    public List<String> sourcefiles;
//    public String entryclass, serviceclass, workbenchpath;
//    
//    public CompileData() {
//        sourcefiles = new ArrayList<>();
//    }
//}
//
//class JarFilter implements FilenameFilter {
//
//    @Override
//    public boolean accept(File arg0, String name) {
//        return name.toLowerCase().endsWith(".jar");
//    }
//    
}