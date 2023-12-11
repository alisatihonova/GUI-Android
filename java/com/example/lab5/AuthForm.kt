package com.example.lab5

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AuthForm : Activity() {

    private val dbh = DBHandler(this)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.onStart()
        Log.i("Информационный лог","AuthForm onCreate");

        setContentView(R.layout.auth_form)

        val signInButton = findViewById<Button>(R.id.signInButton)
        val regButton = findViewById<Button>(R.id.registerButton)
        val login = findViewById<TextView>(R.id.loginText)
        val password = findViewById<TextView>(R.id.passwordText)

        signInButton.setOnClickListener(View.OnClickListener () {

            if (login.text.length == 0 || password.text.length == 0) {
                //Пароль/логин не заполнен
                val toast = Toast.makeText(
                    applicationContext,
                    "Чего-то не хватает!", Toast.LENGTH_SHORT
                )
                toast.show()
            }
            else {
                //Пароль и логин на месте
                val testUser = dbh.getUser(login.text.toString())

                if (testUser.id == -1) {
                    //Пользователя еще нет в БД
                    val toast = Toast.makeText(applicationContext,"Пользователь не найден", Toast.LENGTH_SHORT)
                    toast.show()
                    login.text = ""
                    password.text = ""
                }
                else {
                    //Пользователь с таким логином найден, надо проверить пароль

                    if (testUser.password == password.text.toString()){
                        //Пароли совпали
                        //Запись в интент, который уйдет к основной активити логина, пароля и сообщения
                        val  toListIntent = Intent(this, ListActivity::class.java)
                        toListIntent.putExtra("login", login.text)
                        toListIntent.putExtra("password", password.text)
                        toListIntent.putExtra("important", "Очень важная информация")
                        login.text = ""
                        password.text = ""
                        startActivity(toListIntent)
                    }
                    else {
                        //Пароли не совпали
                        val toast = Toast.makeText(applicationContext,"Введен неверный пароль", Toast.LENGTH_SHORT)
                        toast.show()
                        password.text = ""
                    }
                }
            }
        }
        )

        //Переход к форме регистрации
        regButton.setOnClickListener(View.OnClickListener () {
            val  toRegisterIntent = Intent(this, RegisterActivity::class.java)
            //login.text = ""
            //password.text = ""
            startActivity(toRegisterIntent)
        }
        )
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("Информационный лог","AuthForm onStart");
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("Информационный лог","AuthForm onResume");
    }

    override fun onPause()
    {
        super.onPause()
        Log.i("Информационный лог","AuthForm onPause");
    }

    override fun onStop()
    {
        super.onStop()
        Log.i("Информационный лог","AuthForm onStop");
    }

    override fun onDestroy()
    {
        super.onDestroy()
        dbh.close()
        Log.i("Информационный лог","AuthForm уничтожена");
    }
}

