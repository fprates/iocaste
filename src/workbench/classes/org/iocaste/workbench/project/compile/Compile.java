package org.iocaste.workbench.project.compile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryInstance;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class Compile extends AbstractCommand {
    private CompileData data;
    private List<ConfigFile> configs;
    
    public Compile(Context extcontext) {
        super("compile", extcontext);
        PageBuilderContext context = extcontext.getContext();
        data = new CompileData();
        configs = new ArrayList<>();
        configs.add(new WebConfigFile(context));
        configs.add(new InstallConfigFile(context));
        configs.add(new ViewConfigFile(context));
    }
    
    private final String compile(CompileData data) {
        Directory source;
        String content;
        Map<String, String> texts;
        Map<Object, ComplexDocument> packages;
        Map<Object, ExtendedObject> classes;
        ComplexDocument _package;
        String[] path;
        DirectoryInstance file;
        ExtendedObject _class;
        List<String> lpath;
        boolean proceed = false;
        
        lpath = new ArrayList<>();
        source = new Directory(data.project);
        packages = data.extcontext.project.getDocumentsMap("class");
        for (Object packagekey : packages.keySet()) {
            _package = packages.get(packagekey);
            classes = _package.getItemsMap("class");
            path = getPath(lpath, _package.getstKey());
            source.addDir(path);
            for (Object classkey : classes.keySet()) {
                _class = classes.get(classkey);
                path = getPath(lpath, _class.getst("FULL_NAME"));
                path[path.length - 1] = path[path.length - 1].concat(".java");
                texts = textget(data.project, _class.getst("CLASS_ID"));
                for (String key : texts.keySet()) {
                    content = texts.get(key);
                    file = source.file(source.addFile(path));
                    file.content(content);
                    proceed = true;
                    break;
                }
            }
        }
        
        if (!proceed)
            return null;
        data.source = true;
        return new Iocaste(data.context.function).compile(data.project, source);
    }
    
    private final String deployApplication(CompileData data) throws Exception {
        String[] javaargs;
        String error;
        Iocaste iocaste;
        Directory war;
        DirectoryInstance file;
        
        data.entryclass =
                "org.iocaste.workbench.common.engine.ApplicationEngine";
        
        error = compile(data);
        if (error != null)
            return error;
        
        war = new Directory(data.project.concat(".war"));
        war.addDir("META-INF");
        war.addDir("WEB-INF");
        
        for (ConfigFile config : configs) {
            config.clear();
            config.run(data);
            config.save(war);
        }

        war.addDir("WEB-INF", "lib");
        file = war.copy(war.addFile("WEB-INF", "lib", "iocaste-workbench.jar"));
        file.source("WORKBENCH_LIBS", "iocaste-workbench.jar");
        file = war.copy(war.addFile("WEB-INF", "lib", "iocaste.jar"));
        file.source("WORKBENCH_LIBS", "iocaste.jar");

        if (data.source) {
            javaargs =new String[] {data.project};
            file = war.copy(war.addDir("WEB-INF", "classes"));
            file.dirargs("FULL_JAVA_BIN", javaargs);

            file = war.copy(war.addDir("WEB-INF", "src"));
            file.dirargs("FULL_JAVA_SOURCE", javaargs);
        }
        
        iocaste = new Iocaste(data.context.function);
        iocaste.write("WEBAPPS", war, Iocaste.JAR);
        return null;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) throws Exception {
        ComplexDocument document;
        String error;
        
        data.extcontext = getExtendedContext();
        data.project = parameters.get("project");
        if ((data.project == null) && (data.extcontext.project == null)) {
            message(Const.ERROR, "undefined.project");
            return null;
        }
        
        if (data.project == null)
            data.project = data.extcontext.project.getstKey();
        
        document = getDocument("WB_PROJECT", data.project);
        if (document == null) {
            message(Const.ERROR, "invalid.project");
            return null;
        }
        
        data.extcontext = getExtendedContext();
        data.context = context;
        error = deployApplication(data);
        if (error != null) {
            print(error);
            message(Const.ERROR, "compiling.error");
            return null;
        }
        message(Const.STATUS, "project.compiled");
        return null;
    }
    
    private String[] getPath(List<String> lpath, String path) {
        String[] apath = path.split("\\.");
        lpath.clear();
        for (String token : apath)
            lpath.add(token);
        return lpath.toArray(new String[0]);
    }
}
