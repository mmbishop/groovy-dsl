package com.bishop.dsl.email.util

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Attachments
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.apache.commons.codec.binary.Base64
import org.apache.tika.Tika
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.activation.MimetypesFileTypeMap
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class)

    private String apiKey = null

    void sendEmail(String recipient, String sender, String subjectText, String bodyText, Map<String, List<String>> attachmentMap) {
        Email from = new Email(sender)
        Email to = new Email(recipient)
        Content content = new Content("text/html", getContents(bodyText, attachmentMap))
        Mail mail = new Mail(from, subjectText, to, content)
        Base64 base64 = new Base64()
        Tika tika = new Tika()
        attachmentMap?.each { key, values ->
            values.each { value ->
                String attachmentPath = value[0]
                Attachments attachments = new Attachments()
                File file = new File(attachmentPath)
                String encodedString = base64.encodeAsString(file.bytes)
                attachments.setContent(encodedString)
                attachments.setDisposition("attachment")
                attachments.setFilename(getFilename(attachmentPath))
                attachments.setType(tika.detect(attachmentPath))
                mail.addAttachments(attachments)
            }
        }
        SendGrid sg = new SendGrid(getApiKey())
        Request request = new Request()
        try {
            request.setMethod(Method.POST)
            request.setEndpoint("mail/send")
            request.setBody(mail.build())
            Response response = sg.api(request)
            LOGGER.info("Sendgrid response code = " + response.getStatusCode())
        }
        catch (IOException e) {
            LOGGER.error("Unable to send email", e)
        }
    }

    String getFilename(String path) {
        return path.substring(path.lastIndexOf(System.getProperty("file.separator")) + 1)
    }

    String getContents(String bodyText, Map<String, List<String>> attachmentMap) {
        StringBuilder contents = new StringBuilder("<html>")
        contents.append(bodyText)
        if (attachmentMap && ! attachmentMap.isEmpty()) {
            contents.append("<p/>")
            attachmentMap.each { attachmentType, attachmentList ->
                attachmentList.each { attachment ->
                    contents.append(attachmentType).append(" ").append(attachment[0].toString()).append(" is attached.<br>")
                }
            }
        }
        contents.append("</html>")
        return contents.toString()
    }

    String getApiKey() {
        if (apiKey == null) {
            Properties props = new Properties()
            InputStream stream = getClass().getResourceAsStream("/application.properties")
            props.load(stream)
            return props.get("sendgrid.api-key")
        }
        return apiKey
    }

}
