package org.iocaste.workbench;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class Build extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String filename = message.getst("file");
        Iocaste iocaste = new Iocaste(getFunction());
        List<FileEntry> entries = iocaste.getFiles("workbench", filename);
        
//        iocaste.compile("workbench", filename)
//        
//        CompilationTask task;
//        Writer writer;
//        StandardJavaFileManager filemngr;
//        Iterable<? extends JavaFileObject> cunits;
//        List<String> options;
//        Locale locale = new Iocaste(getFunction()).getLocale();
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//        filemngr = compiler.getStandardFileManager(null, locale, null);
//        cunits = filemngr.getJavaFileObjectsFromFiles(files);
//        writer = new StringWriter();
//        task = compiler.getTask(writer, filemngr, null, options, null, cunits);
        return null;
    }

}
