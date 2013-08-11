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
        prefix = new StringBuilder(context.path).
                append(File.separator).append("WEB-INF").
                append(File.separator).append("lib").
                append(File.separator).toString();
        
        file = new File(prefix);
        cp = new StringBuilder();
        for (String filename : file.list(new JarFilter())) {
            if (cp.length() > 0)
                cp.append(":");
        
            cp.append(prefix).append(filename);
        }
        
        options = new ArrayList<>();
        options.addAll(Arrays.asList("-cp", cp.toString()));
        options.addAll(Arrays.asList("-d",
                new StringBuilder(context.project.dir).
                append(File.separator).append("bin").
                append(File.separator).append("WEB-INF").
                append(File.separator).append("classes").toString()));
        
        writer = new StringWriter();
        task = compiler.getTask(writer, fmngr, null, options, null, cunits);
        prefix = (task.call())? null : writer.toString();
        writer.close();
        fmngr.close();
        
        return prefix;
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
        String libfrom = context.path+"/WEB-INF/lib";
        String libto = context.project.dir+"/bin/WEB-INF/lib";
        
        new File(libto).mkdir();
        for (File file : new File(libfrom).listFiles())
            copyFile(new File(libto+File.separator+file.getName()), file);
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
            context.project.dir = new StringBuilder(context.repository).
                  append(File.separator).append(projectname).toString();
        
        removeCompleteDir(context.project.dir);
        new File(context.project.dir).mkdir();
        bindir = new StringBuilder(context.project.dir);
        for (String dirname : new String[] {"bin", "WEB-INF", "classes"})
            new File(bindir.append(File.separator).
                    append(dirname).toString()).mkdir();
        
        for (String packagename : context.project.packages.keySet()) {
            dir = packagename.replaceAll("[\\.]", File.separator);
            dir = new StringBuilder(context.project.dir).
                    append(File.separator).append("src").
                    append(File.separator).append(dir).toString();
            new File(dir).mkdirs();
            
            package_ = context.project.packages.get(packagename);
            for (String sourcename : package_.sources.keySet()) {
                source = package_.sources.get(sourcename);                
                source.filename = new StringBuilder(dir).
                        append(File.separator).append(sourcename).toString();
                
                file = new File(source.filename);
                file.createNewFile();
                os = new FileOutputStream(file, false);
                os.write(source.code.getBytes());
                os.flush();
                os.close();
            }
        }
    }
    
    private static final void deployApplication(Context context)
            throws Exception {
        String jarname = context.project.header.getValue("NAME");
        String dest = new StringBuilder(System.getProperty("catalina.home")).
                append(File.separator).append("webapps").
                append(File.separator).append(jarname).
                append(".war").toString();
        OutputStream os = new FileOutputStream(dest);
        JarOutputStream jar = new JarOutputStream(os);
        String bindir = new StringBuilder(context.project.dir).
                append(File.separator).append("bin").toString();
        
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