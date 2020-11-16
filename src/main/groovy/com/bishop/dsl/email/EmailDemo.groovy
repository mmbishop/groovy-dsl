package com.bishop.dsl.email

import static com.bishop.dsl.email.Email.email

class EmailDemo {

    static void main(String[] args) {
        def recipients = ["recipient@somewhere.com"]
        def emails = []

        recipients.each { recipient ->
            emails << email {
                to recipient
                from "sender@somewhere.com"
                subject "Greetings from Groovy DSL"
                body "Wassup!"
                image "/Users/Me/image1.jpg"
                image "/Users/Me/image2.jpg"
            }
        }

        emails.each { email -> email.send() }
    }

}
