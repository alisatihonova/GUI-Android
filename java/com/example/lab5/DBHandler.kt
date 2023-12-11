package com.example.lab5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHandler (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NOTUsers.db"
    }

    //Создание БД из одной таблицы "Users"
    override fun onCreate(db: SQLiteDatabase) {
        //Создание таблицы
        //В результате получится SQL-запрос
        //CREATE TABLE $Users.db (id INTEGER PRIMARY KEY,login TEXT,password TEXT)
        val createUsersTable = "CREATE TABLE ${DBContract.Table.TABLE_NAME} (" +
                "${DBContract.Table.COLUMN_NAME_KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," + //первичный ключ
                "${DBContract.Table.COLUMN_NAME_LOGIN} TEXT," +
                "${DBContract.Table.COLUMN_NAME_PASS} TEXT)"
        db.execSQL(createUsersTable)
    }

    //Обновление базы данных
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.Table.TABLE_NAME}")
        onCreate(db)
    }

    //Добавление пользователя в базу данных
    fun addUser(user: User): Boolean {
        val db = this.writableDatabase

        //Проверка, существует ли пользователь с таким логином
        val checkQuery = "SELECT * FROM ${DBContract.Table.TABLE_NAME} WHERE ${DBContract.Table.COLUMN_NAME_LOGIN} = '" + user.login + "'"
        val cursor: Cursor = db.rawQuery(checkQuery, null)

        //Логин свободен
        if (cursor.count == 0) {
            val values = ContentValues()

            values.put(DBContract.Table.COLUMN_NAME_LOGIN, user.login)
            values.put(DBContract.Table.COLUMN_NAME_PASS, user.password)

            db.insert(DBContract.Table.TABLE_NAME, null, values)
            cursor.close()
            return true
        }
        //Логин занят
        else {
            cursor.close()
            return false
        }

    }

    //Удаление всего на свете
    fun deleteAll() {
        writableDatabase.execSQL("DELETE FROM ${DBContract.Table.TABLE_NAME}")
    }

    //Получение всех записей из базы данных
    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val selectQuery = "SELECT * FROM ${DBContract.Table.TABLE_NAME}"
        val db = this.writableDatabase

        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.Table.COLUMN_NAME_KEY_ID)
                ).toInt()
                val login = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.Table.COLUMN_NAME_LOGIN)
                )
                val pass = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.Table.COLUMN_NAME_PASS)
                )
                val user = User(id = id, login = login, password = pass)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    //Получить запись пользователя по логину
    //И потом проверить В АКТИВИТИ нашлось ли чего
    fun getUser(userLogin: String): User {
        val selectQuery = "SELECT * FROM ${DBContract.Table.TABLE_NAME} WHERE ${DBContract.Table.COLUMN_NAME_LOGIN} = '" + userLogin + "'"
        val db = this.writableDatabase
        var user = User(-1, "null", "null")

        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.Table.COLUMN_NAME_KEY_ID)
                ).toInt()
                val login = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.Table.COLUMN_NAME_LOGIN)
                )
                val pass = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.Table.COLUMN_NAME_PASS)
                )
                user = User(id = id, login = login, password = pass)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return user
    }

    //Удаление всего на свете
    fun deleteUser(userLogin: String) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM ${DBContract.Table.TABLE_NAME} WHERE ${DBContract.Table.COLUMN_NAME_LOGIN} = '" + userLogin + "'")
    }

    fun updatePassword(userLogin: String, newPassword: String) {
        val db = this.writableDatabase
        val updateQuery = "UPDATE ${DBContract.Table.TABLE_NAME} SET ${DBContract.Table.COLUMN_NAME_PASS} = '" + newPassword + "' WHERE ${DBContract.Table.COLUMN_NAME_LOGIN} = '" + userLogin + "'"
        db.execSQL(updateQuery)
    }
}