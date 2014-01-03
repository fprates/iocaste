package org.iocaste.workbench;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Request {

//    public static final void addscreen(Context context) {
//        context.view.redirect("screeneditor");
//    }
//    
//    /**
//     * 
//     * @param packages
//     * @param context
//     * @param documents
//     */
//    private static final void loadPackages(ExtendedObject[] packages,
//            Context context, Documents documents) {
//        ProjectPackage package_;
//        String packagename;
//        long packageid = 0;
//        
//        context.project.packages.clear();
//        for (ExtendedObject object : packages) {
//            package_ = new ProjectPackage();
//            packagename = object.get("NAME");
//            if (packageid == 0) {
//                context.project.header.set("PACKAGE", packagename);
//                context.project.entryclass = packagename.concat(".Main");
//            }
//            
//            packageid = object.getl("ID");
//            context.project.packages.put(packagename, package_);
//            
//            loadSource(packageid, documents, context, package_);
//        }
//    }
//    
//    /**
//     * 
//     * @param packageid
//     * @param documents
//     * @param context
//     * @param package_
//     */
//    private static final void loadSource(long packageid, Documents documents,
//            Context context, ProjectPackage package_) {
////        Query query;
////        ExtendedObject[] sources, srccode;
////        String sourcename;
////        StringBuilder code;
////        Source source;
////        boolean paragraph;
////        long sourceid = 0;
////
////        query = new Query();
////        query.setModel("ICSTPRJ_SRCCODE");
////        query.andEqual("SOURCE",  packageid);
////        sources = documents.select(query);
////        if (sources == null)
////            return;
////        
////        for (ExtendedObject object_ : sources) {
////            sourcename = object_.get("NAME");
////            if (sourceid == 0)
////                context.project.header.set("CLASS", sourcename);
////            
////            sourceid = object_.getl("ID");
////            source = new Source();
////            package_.sources.put(sourcename, source);
////            
////            srccode = documents.select(
////                    Common.QUERIES[Common.SEL_SRCCODE], sourceid);
////            if (srccode == null)
////                continue;
////            
////            code = new StringBuilder();
////            for (ExtendedObject linecode : srccode) {
////                paragraph = linecode.get("PARAGRAPH");
////                if (code.length() > 0 && paragraph == true)
////                    code.append("\r\n");
////                    
////                code.append(linecode.get("LINE"));
////            }
////
////            source.code = code.toString();
////        }
//    }
}

//class CodeLineHelper {
//    public boolean paragraph;
//    public String line;
//    public long sourceid, packageid, i;
//    public Context context;
//    public Documents documents;
//}