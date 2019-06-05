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
    private Map<String, List<String>> attachmentMap = [:]

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

    def methodMissing(String methodName, def args) {
        if (! attachmentMap[methodName]) {
            attachmentMap[methodName] = []
        }
        attachmentMap[methodName] << args
    }

    String getRecipient() {
        return recipient
    }

    String getSender() {
        return sender
    }

    String getSubjectText() {
        return subjectText
    }

    String getBodyText() {
        return bodyText
    }

    Map<String, List<String>> getAttachmentMap() {
        return attachmentMap
    }

    void setAttachmentMap(Map<String, List<String>> attachmentMap) {
        this.attachmentMap = attachmentMap
    }

    def getSend() {
        send()
    }

    void send() {
        EmailSender.sendEmail(this);
    }

}
