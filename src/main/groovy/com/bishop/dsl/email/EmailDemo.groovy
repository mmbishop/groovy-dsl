package com.bishop.dsl.email

import static com.bishop.dsl.email.Email.email

class EmailDemo {

    static void main(String[] args) {
        def recipients = ["recipient@gmail.com"]
        def emails = []

        recipients.each { recipient ->
            emails << email {
                to recipient
                from "sender@gmail.com"
                subject "Greetings"
                body "Wassup!"
                image "/path/to/image1"
                image "/path/to/image2"
            }
        }

        emails.each { email -> email.send() }
    }

}
