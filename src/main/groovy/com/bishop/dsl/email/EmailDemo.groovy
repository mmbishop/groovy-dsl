package com.bishop.dsl.email

import static com.bishop.dsl.email.Email.email

class EmailDemo {

    static void main(String[] args) {
        def recipients = ["tend2dv8@gmail.com"]
        def emails = []

        recipients.each { recipient ->
            emails << email {
                to recipient
                from "sender@gmail.com"
                subject "Greetings"
                body "Wassup!"
                image "/Users/Michael/Pictures/david.jpg"
                image "/Users/Michael/Pictures/samuel.jpg"
            }
        }

        emails.each { email -> email.send() }
    }

}
