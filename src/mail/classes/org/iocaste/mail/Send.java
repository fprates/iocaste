package org.iocaste.mail;

import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.iocaste.mail.common.MailData;
import org.iocaste.mail.common.MailDocument;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Send extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        javax.mail.Message mailmessage;
        Session session;
        Set<String> to;
        MailData data = message.get("data");
        Properties properties = new Properties();

        properties.put("mail.smtp.host", data.smtphost);
        properties.put("mail.smtp.port", data.smtpport);
        properties.put("mail.smtp.auth", data.smtpauth);
        properties.put("mail.smtp.ssl.enable", data.smtpsslenable);
        session = Session.getInstance(properties);
        
        for (MailDocument document : data.documents) {
            to = document.getTo();
            if (to.size() == 0)
                throw new IocasteException("no recipients defined.");
            
            mailmessage = new MimeMessage(session);
            mailmessage.setFrom(new InternetAddress(document.getFrom()));
            mailmessage.setSubject(document.getSubject());
            mailmessage.setHeader("X-Mailer", "iocastemailsndr");
            mailmessage.setSentDate(new Date());
            mailmessage.setText(document.getContent());
            for (String toitem : to) {
                mailmessage.setRecipients(javax.mail.Message.RecipientType.TO,
                        InternetAddress.parse(toitem, false));
                try {
                    Transport.send(mailmessage, data.user, data.secret);
                } catch (Exception e) {
                    throw new IocasteException(e.getMessage());
                }
            }
        }
        
        return null;
    }
}
