package com.bishop.dsl.email

class EmailCredentials {

    private String username = null
    private String password = null

    private void load() {
        Properties props = new Properties()
        InputStream stream = getClass().getResourceAsStream("/application.properties")
        props.load(stream)
        username = props.get("email.username")
        password = props.get("email.password")
    }

    String getUsername() {
        if (username == null) {
            load()
        }
        return username
    }

    String getPassword() {
        if (password == null) {
            load()
        }
        return password
    }

}
