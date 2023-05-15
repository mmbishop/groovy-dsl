package com.bishop.dsl.email

import com.bishop.dsl.email.util.EmailSender

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

    def getSend() {
        send()
    }

    void send() {
        new EmailSender().sendEmail(recipient, sender, subjectText, bodyText, attachmentMap)
    }

}
