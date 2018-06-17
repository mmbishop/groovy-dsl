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

class Email {

    private String recipient
    private String sender
    private String subjectText
    private String bodyText
    def attachmentMap = [:]

    static Email email(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=Email) Closure closure) {
        Email e = new Email()
        closure.delegate = e
        closure()
        return e
    }

    void to(String recipient) {
        this.recipient = recipient
    }

    void from(String sender) {
        this.sender = sender
    }

    void subject(String subjectText) {
        this.subjectText = subjectText
    }

    void body(String bodyText) {
        this.bodyText = bodyText
    }

    def methodMissing(String methodName, args) {
        if (! attachmentMap[methodName]) {
            attachmentMap[methodName] = []
        }
        attachmentMap[methodName] << args
    }

    def getSend() {
        send()
    }

    void send() {
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
        message.setFrom(new InternetAddress(sender))
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient))
        message.setSubject(subjectText)
        Multipart multipart = new MimeMultipart()

        attachmentMap.each { key, values ->
            values.each { value ->
                BodyPart attachmentPart = new MimeBodyPart()
                String filename = value[0]
                DataSource source = new FileDataSource(filename)
                attachmentPart.setDataHandler(new DataHandler(source))
                attachmentPart.setFileName(filename)
                multipart.addBodyPart(attachmentPart)
                bodyText += "\n" + key + " " + filename + " is attached."
            }
        }

        BodyPart textBodyPart = new MimeBodyPart()
        textBodyPart.setText(bodyText)
        multipart.addBodyPart(textBodyPart)

        message.setContent(multipart)

        Transport transport = session.getTransport("smtps")
        transport.connect(host, credentials.username, credentials.password)
        transport.sendMessage(message, message.getAllRecipients())
        transport.close()
    }

}
