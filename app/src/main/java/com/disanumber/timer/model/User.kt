package com.disanumber.timer.model

class User {

    var name: String = ""
    var email: String = ""
    var version: String = ""


    constructor(name: String, email: String, version: String) {
        this.name = name
        this.email = email
        this.version = version
    }

    constructor()
}