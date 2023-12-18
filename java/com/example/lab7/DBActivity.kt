package com.example.lab7

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DBActivity : Activity () {
    private val dbh = DBHandler(this)
    private lateinit var rvUsers: RecyclerView
    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.onStart()
        Log.i("Информационный лог", "AuthForm onCreate");

        setContentView(R.layout.db_form)
        val backButton = findViewById<Button>(R.id.backToSettingsButton)

        val users = dbh.getAllUsers()
        val usersLog = users.joinToString(separator = ",\n")
        userAdapter.setData(users)

        rvUsers = findViewById(R.id.rv_users)
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvUsers.adapter = userAdapter


        backButton.setOnClickListener(View.OnClickListener () {
            finish()
        })
    }
}