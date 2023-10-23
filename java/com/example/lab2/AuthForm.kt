package com.example.lab2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast




class AuthForm : Activity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                toListIntent.putExtra("password", password.text)
                login.text = ""
                password.text = ""
                startActivity(toListIntent)
            }

            login.text = ""
            password.text = ""
            }
        )
    }


}

