package org.iocaste.report;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.report.common.ReportParameters;

import com.lowagie.text.DocumentException;

public class Services extends AbstractFunction {
    private DataSource ds;

    public Services() {
        Context initContext;
        try {
            initContext = new InitialContext();
            ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/iocaste");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        
        export("get_content", "getContent");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final byte[] getContent(Message message) throws Exception {
        JasperPrint jprint;
        ReportParameters parameters = message.get("parameters");
        Connection connection = ds.getConnection();
        
        try {
            jprint = JasperFillManager.fillReport(
                    parameters.getReportFile(),
                    parameters.getAttributes(),
                    connection);
            
            switch (parameters.getContentFormat()) {
            case ReportParameters.PDF:
                return JasperExportManager.exportReportToPdf(jprint);
                
            case ReportParameters.XML:
                return JasperExportManager.exportReportToXml(jprint).getBytes();
                
            default:
                throw new Exception("invalid output format.");
            }
            
        } catch (JRException e) {
            throw new Exception(e.getMessage());   
        } catch (DocumentException e) {
            throw new Exception(e.getMessage());
        } finally {
            connection.close();
        }
    }
}
