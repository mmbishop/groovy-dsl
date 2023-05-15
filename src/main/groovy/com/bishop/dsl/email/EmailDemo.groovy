package com.bishop.dsl.email

import static com.bishop.dsl.email.Email.email

class EmailDemo {

    static void main(String[] args) {
        def recipients = ["curmudgeoncoder@gmail.com"]
        def emails = []

        recipients.each { recipient ->
            emails << email {
                to recipient
                from "tend2dv8@gmail.com"
                subject "Greetings from Groovy DSL"
                body "Wassup!"
                image "/Users/Michael/Pictures/David/david-school-portrait-2020-2021.jpg"
                image "/Users/Michael/Pictures/Sam/sam-school-portrait-2020-2021.jpg"
            }
        }

        emails.each { email -> email.send() }
    }

}
