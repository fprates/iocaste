package org.iocaste.workbench;

public class Activation {
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
//    private static final void deployApplication(Context context)
//            throws Exception {
//        String jarname = context.project.header.get("NAME");
//        String dest = composeFileName(System.getProperty("catalina.home"),
//                "webapps", jarname.concat(".war"));
//        OutputStream os = new FileOutputStream(dest);
//        JarOutputStream jar = new JarOutputStream(os);
//        String bindir = composeFileName(context.project.dir, "bin");
//        
//        addJarItems(jar, bindir, bindir);
//        jar.close();
//        os.close();
//    }
//    
//    public static final void start(Context context) throws Exception {
//        createProjectFiles(context);
//        compileProject(context);
//        deployApplication(context);
//    }
}
/*
class JarFilter implements FilenameFilter {

    @Override
    public boolean accept(File arg0, String name) {
        return name.toLowerCase().endsWith(".jar");
    }
    
}*/