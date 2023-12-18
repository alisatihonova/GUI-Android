package com.example.lab7

import android.provider.BaseColumns

class DBContract {
    object Table : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_KEY_ID = "id"
        const val COLUMN_NAME_LOGIN = "login"
        const val COLUMN_NAME_PASS = "password"
    }
}