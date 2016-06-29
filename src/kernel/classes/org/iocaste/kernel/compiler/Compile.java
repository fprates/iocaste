package org.iocaste.kernel.compiler;

import java.io.File;
import java.io.FilenameFilter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.iocaste.kernel.files.DeleteFile;
import org.iocaste.kernel.files.FileServices;
import org.iocaste.kernel.files.MakeDirectory;
import org.iocaste.kernel.files.directory.DirectoryWrite;
import org.iocaste.kernel.session.GetLocale;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.Directory;

public class Compile extends AbstractHandler {

    private final String createdir(FileServices services, String symbol) {
        String dir = FileServices.getSymbolPath(symbol);
        DeleteFile delete = services.get("delete");
        MakeDirectory mkdir = services.get("mkdir");

        delete.run(true, dir);
        mkdir.run(dir);
        return FileServices.getPath(dir);
    }
    
    private final String getClassPath(String classpath) {
        String libs = FileServices.getSymbolPath("WORKBENCH_LIBS");
        File file = new File(libs);
        StringBuilder cp = null;
        for (String filename : file.list(new JarFilter())) {
            if (cp != null)
                cp.append(":");
            else
                cp = new StringBuilder();
            cp.append(libs).append(filename);
        }
        return cp.toString();
    }
    
    private final void retrieveFiles(List<File> files, File file) {

        if (file.isFile()) {
            files.add(file);
            return;
        }
        
        for (File child : file.listFiles())
            retrieveFiles(files, child);
    }
    
    @Override
    public Object run(Message message) throws Exception {
        CompilationTask task;
        Writer writer;
        List<String> options;
        StandardJavaFileManager fmngr;
        Iterable<? extends JavaFileObject> cunits;
        File file;
        Directory source;
        GetLocale localeget;
        DirectoryWrite directorywrite;
        CompilerService service;
        String sessionid, output, classpath, javabin;
        List<File> files = new ArrayList<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null)
            return "compiler.unavailable";
        
        service = getFunction();
        source = message.get("source");
        sessionid = message.getSessionid();
        directorywrite = service.files.get("directory_write");
        directorywrite.run(sessionid, "JAVA_SOURCE", source, Iocaste.RAW);
        
        localeget = service.session.get("get_locale");
        fmngr = compiler.getStandardFileManager(
                null, localeget.run(sessionid), null);
        
        file = new File(FileServices.getPath(
                FileServices.getSymbolPath("JAVA_SOURCE")));
        retrieveFiles(files, file);
        cunits = fmngr.getJavaFileObjects(files.toArray(new File[0]));
      
        classpath = getClassPath(message.getst("classpath"));
        javabin = createdir(service.files, "JAVA_BIN");
        options = new ArrayList<>();
        options.addAll(Arrays.asList("-cp", classpath));
        options.addAll(Arrays.asList("-d", javabin));
      
        writer = new StringWriter();
        task = compiler.getTask(writer, fmngr, null, options, null, cunits);
        output = (task.call())? null : writer.toString();
        writer.flush();
        writer.close();
        fmngr.close();
        
        return output;
    }

}

class JarFilter implements FilenameFilter {

    @Override
    public boolean accept(File arg0, String name) {
        return name.toLowerCase().endsWith(".jar");
    }
    
}