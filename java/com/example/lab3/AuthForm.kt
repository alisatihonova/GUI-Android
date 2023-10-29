package com.example.lab3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AuthForm : Activity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.onStart()
        Log.i("Информационный лог","Создан в методе onCreate");

        setContentView(R.layout.auth_form)

        val signInButton = findViewById<Button>(R.id.signInButton)
        val login = findViewById<TextView>(R.id.loginText)
        val password = findViewById<TextView>(R.id.passwordText)

        signInButton.setOnClickListener(View.OnClickListener () {

            if (login.text.length == 0 || password.text.length == 0) {

                val toast = Toast.makeText(
                    applicationContext,
                    "Чего-то не хватает!", Toast.LENGTH_SHORT
                )
                toast.show()
            }
            else {
                val  toListIntent = Intent(this, ListActivity::class.java)
                toListIntent.putExtra("login", login.text)
                toListIntent.putExtra("important", "Очень важная информация")
                login.text = ""
                password.text = ""
                startActivity(toListIntent)
            }

            login.text = ""
            password.text = ""
            }
        )
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("Информационный лог","Создан в методе onStart");
    }

    override fun onResume()
    {
        super.onResume()
        Log.e("Лог с ошибкой","Самый приятный, создан в методе onResume");
    }

    override fun onPause()
    {
        super.onPause()
        Log.w("Лог с предупреждением","Создан в методе onPause");
    }

    override fun onStop()
    {
        super.onStop()
        Log.d("Дебажный лог","Создан в методе onStop");
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.v("Лог с подробностями","Создан в методе onDestroy");
    }
}

