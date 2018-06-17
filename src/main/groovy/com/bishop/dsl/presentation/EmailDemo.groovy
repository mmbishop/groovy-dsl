package com.bishop.dsl.presentation

import com.bishop.dsl.email.Email
import static com.bishop.dsl.email.Email.email

public class EmailDemo {

    public static void main(String[] args) {

        def recipients = ["tend2dv8@gmail.com"]
        def emails = []
        recipients.each { recipient ->
            emails << email {
                to recipient
                from "mbishop.extremetix@gmail.com"
                subject "Greetings"
                body "How's it going?"
                image "C:\\Users\\Michael\\Pictures\\david.jpg"
                image "C:\\Users\\Michael\\Pictures\\samuel.jpg"
            }
        }
        emails.each { it.send() }

    }

}