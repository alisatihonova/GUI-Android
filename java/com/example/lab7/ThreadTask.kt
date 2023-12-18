package com.example.lab2

import android.os.Handler
import android.os.Message
import com.example.lab7.User
import com.example.lab7.DBHandler
import java.lang.Boolean
import kotlin.String

class ThreadTask internal constructor(var thr_handler: Handler, db: DBHandler) {
    var dbh: DBHandler
    val message = Message.obtain()

    init {
        this.dbh = db
    }

    fun authTask(login: String) {
        Thread {
            val tmpUser: User = dbh.getUser(login)

            /*if ()) {
                message.obj = Boolean.TRUE
            } else message.obj = Boolean.FALSE
            message.sendingUid = 1
            thr_handler.sendMessage(message)*/
        }.start()
    }

    fun registrationTask(login: String?, password: String?) {
        Thread {
            /*if (dbh.getUserByUsername(login)) {
                message.obj = Boolean.FALSE
            } else {
                dbh.addUser(login, password)
                message.obj = Boolean.TRUE
            }
            message.sendingUid = 2
            thr_handler.sendMessage(message)*/
        }.start()
    }

    fun deleteTask(login: String?) {
        Thread {
            /*if (!dbh.getUserByUsername(login)) {
                message.obj = Boolean.FALSE
            } else {
                dbh.deleteUser(login)
                message.obj = Boolean.TRUE
            }
            message.sendingUid = 3
            thr_handler.sendMessage(message)*/
        }.start()
    }

    fun changePswdTask(login: String?, password: String?, oldPassword: String?) {
        Thread {
           /* if (!dbh.checkUser(login, password)) {
                message.obj = Boolean.FALSE
            } else {
                dbh.changePswd(login, password, oldPassword)
                message.obj = Boolean.TRUE
            }
            message.sendingUid = 4
            thr_handler.sendMessage(message)*/
        }.start()
    }
}