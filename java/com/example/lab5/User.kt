package com.example.lab5

import java.security.MessageDigest

class User {
    var id: Int = -1
    var login: String = ""
    var password: String = ""

    constructor() {
        this.id = -1
        this.login = ""
        this.password = ""
    }

    constructor(id: Int, login: String, password: String) {
        this.id = id
        this.login = login
        this.password = password
    }

    constructor(login: String, password: String) {
        this.id = -1
        this.login = login
        this.password = password
    }

}