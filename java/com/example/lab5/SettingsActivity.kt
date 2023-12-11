package com.example.lab5

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SettingsActivity: Activity() {
    private val dbh = DBHandler(this)
    private var user = User()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.onStart()
        Log.i("Информационный лог", "SettingsActivity стартовала");

        setContentView(R.layout.settings_form)

        val newPassword = findViewById<TextView>(R.id.newPasswordText)
        val passwordButton = findViewById<Button>(R.id.passwordButton)
        val databaseButton = findViewById<Button>(R.id.databaseButton)
        val deleteButton = findViewById<TextView>(R.id.deleteButton)
        val backButton = findViewById<TextView>(R.id.backButton)

        //Достаем юзера из интента, нам нужен его логин
        val arguments: Bundle? = intent.extras
        user = User(arguments!!["login"].toString(), arguments!!["password"].toString())

        //Обработчик нажатия на кнопку "Вернуться назад"
        backButton.setOnClickListener(View.OnClickListener () {
            finish();
        }
        )

        //Переход к просмотру БД
        databaseButton.setOnClickListener(View.OnClickListener () {
            val  toDBIntent = Intent(this, DBActivity::class.java)
            toDBIntent.putExtra("login", user.login)
            toDBIntent.putExtra("password", user.password)
            startActivity(toDBIntent)
        }
        )

        //Смена пароля
        passwordButton.setOnClickListener(View.OnClickListener () {
            if (newPassword.text.length != 0) {
                //Новый пароль ввели, все норм
                dbh.updatePassword(user.login, newPassword.text.toString())
                newPassword.text = ""
                Toast.makeText(this@SettingsActivity, "Пароль обновлен", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@SettingsActivity, "Новый пароль не был указан", Toast.LENGTH_SHORT).show()
            }
        })

        //Обработчик нажатия на кнопку "Необратимо удалиться"
        deleteButton.setOnClickListener(View.OnClickListener () {
            //Вызов алерта для подтверждения удаления
            createAlertDialog()
        }
        )
    }

    override fun onDestroy()    {
        super.onDestroy()
        dbh.close()
        Log.i("Информационный лог","SettingsActivity уничтожена");
    }

    public fun deleting () {
        //Удаление юзера из БД по логину
        dbh.deleteUser(user.login)
    }

    private fun createAlertDialog () {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Удаление - это навсегда!")
            .setMessage("Удаление необратимо, если что. Удалиться?")
            .setNegativeButton("Нет, остаемся"){dialog, i ->
                dialog.cancel()
            }
            .setPositiveButton("Да, всем пока!") {dialog, i ->
                deleting()
                dialog.cancel()
                finish()
                val intent = Intent("finish_activity")
                sendBroadcast(intent)
            }
            .show()
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("Информационный лог","SettingsActivity onStart");
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("Информационный лог","SettingsActivity onResume");
    }

    override fun onPause()
    {
        super.onPause()
        Log.i("Информационный лог","SettingsActivity onPause");
    }

    override fun onStop()
    {
        super.onStop()
        Log.i("Информационный лог","SettingsActivity onStop");
    }
}