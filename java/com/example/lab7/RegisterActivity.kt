package com.example.lab7

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class RegisterActivity: Activity() {

    private val dbh = DBHandler(this)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.onStart()
        Log.i("Информационный лог", "RegisterActivity стартовала");

        setContentView(R.layout.registration_form)

        val login = findViewById<TextView>(R.id.regLoginText)
        val password = findViewById<TextView>(R.id.regPasswordText)
        val regButton = findViewById<Button>(R.id.regRegisterButton)
        val backButton = findViewById<Button>(R.id.regBackButton)

        regButton.setOnClickListener(View.OnClickListener () {
            if (login.text.length == 0 || password.text.length == 0) {

                val toast = Toast.makeText(
                    applicationContext,
                    "Чего-то не хватает!", Toast.LENGTH_SHORT
                )
                toast.show()
                login.text = ""
                password.text = ""
            }
            else {
                val newUser = User(login.text.toString(), password.text.toString())

                if (dbh.addUser(newUser)) {
                    //Пользователь добавлен в БД
                    val  toListIntent = Intent(this, ListActivity::class.java)
                    toListIntent.putExtra("login", login.text)
                    toListIntent.putExtra("passwordHash", password.text)
                    toListIntent.putExtra("important", "Очень важная информация")
                    login.text = ""
                    password.text = ""
                    Toast.makeText(this@RegisterActivity, "Пользователь добавлен в БД", Toast.LENGTH_SHORT).show()
                    startActivity(toListIntent)
                }
                else {
                    //Пользователь уже существует
                    val toast = Toast.makeText(
                        applicationContext,
                        "Логин уже занят!", Toast.LENGTH_SHORT
                    )
                    toast.show()
                    login.text = ""
                    password.text = ""
                }
            }
        }
        )

        backButton.setOnClickListener(View.OnClickListener () {
            login.text = ""
            password.text = ""
            finish()
        }
        )
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("Информационный лог","RegisterActivity onStart");
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("Информационный лог","RegisterActivity onResume");
    }

    override fun onPause()
    {
        super.onPause()
        Log.i("Информационный лог","RegisterActivity onPause");
    }

    override fun onStop()
    {
        super.onStop()
        Log.i("Информационный лог","RegisterActivity onStop");
    }

    override fun onDestroy()
    {
        super.onDestroy()
        dbh.close()
        Log.i("Информационный лог","RegisterActivity уничтожена");
    }

}
