package com.bishop.dsl.email

import static com.bishop.dsl.email.Email.email

class EmailDemo {

    static void main(String[] args) {
        def recipients = ["tend2dv8@gmail.com"]
        def emails = []

        recipients.each { recipient ->
            emails << email {
                to recipient
                from "mbishop.extremetix@gmail.com"
                subject "Greetings"
                body "Wassup!"
                image "/home/mike/Pictures/david.jpg"
                image "/home/mike/Pictures/samuel.jpg"
            }
        }

        emails.each { email -> email.send() }
    }

}
