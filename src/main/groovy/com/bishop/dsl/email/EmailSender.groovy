package com.bishop.dsl.email

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

import static com.bishop.dsl.email.Email.email

class EmailSender {

    static void sendEmail(String recipient, String sender, String subjectText, String bodyText) {
        Email email = createEmail(recipient, sender, subjectText, bodyText)
        sendEmail(email)
    }

    static void sendEmail(String recipient, String sender, String subjectText, String bodyText, Map<String, List<String>> attachmentMap) {
        Email email = createEmail(recipient, sender, subjectText, bodyText)
        email.attachmentMap = attachmentMap
        sendEmail(email)
    }

    static void sendEmail(Email email) {
        EmailCredentials credentials = new EmailCredentials()
        String host = "smtp.gmail.com"
        Properties props = new Properties(System.getProperties())
        props.put("mail.smtp.ssl.trust", host)
        props.put("mail.smtp.auth", true)
        props.put("mail.smtps.auth", true)
        props.put("mail.smtp.host", host)
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        props.put("mail.smtp.socketFactory.fallback", false)
        props.put("mail.smtp.socketFactory.port", "465")
        props.put("mail.smtp.user", credentials.username)
        props.put("mail.smtp.password", credentials.password)
        props.put("mail.smtp.port", "465")
        props.put("mail.smtps.quitwait", false)

        Session session = Session.getDefaultInstance(props, null)
        MimeMessage message = new MimeMessage(session)
        message.setFrom(new InternetAddress(email.sender))
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.recipient))
        message.setSubject(email.subjectText)
        Multipart multipart = new MimeMultipart()

        StringBuilder bodyText = new StringBuilder(email.bodyText)

        email.attachmentMap?.each { key, values ->
            values.each { value ->
                BodyPart attachmentPart = new MimeBodyPart()
                String filename = value[0]
                DataSource source = new FileDataSource(filename)
                attachmentPart.setDataHandler(new DataHandler(source))
                attachmentPart.setFileName(filename)
                multipart.addBodyPart(attachmentPart)
                bodyText << "\n" + key + " " + filename + " is attached."
            }
        }

        BodyPart textBodyPart = new MimeBodyPart()
        textBodyPart.setText(bodyText.toString())
        multipart.addBodyPart(textBodyPart)

        message.setContent(multipart)

        Transport transport = session.getTransport("smtps")
        transport.connect(host, credentials.username, credentials.password)
        transport.sendMessage(message, message.getAllRecipients())
        transport.close()
    }

    private static Email createEmail(String recipient, String sender, String subjectText, String bodyText) {
        email {
            to recipient
            from sender
            subject subjectText
            body bodyText
        }
    }

}
