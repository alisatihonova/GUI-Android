package com.example.lab4

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.json.JSONStringer
import java.io.IOException
import java.lang.Math.random
import java.util.Arrays
class ListActivity : Activity(){
    @SuppressLint("MissingInflatedId")

    val thingsArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_form)

        val client = OkHttpClient()

        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val list = findViewById<ListView>(R.id.listOfThings)

        val arguments: Bundle? = intent.extras
        var forToast = arguments!!["important"].toString()
        Toast.makeText(this@ListActivity, forToast, Toast.LENGTH_SHORT).show()
        forToast = arguments!!["login"].toString()
        Toast.makeText(this@ListActivity, "Логин был таким: " + forToast, Toast.LENGTH_SHORT).show()

        val selectedArray = ArrayList<String>()
        val thingsAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, thingsArray)
        list.adapter = thingsAdapter

        //Восстановление списка из SharedPreferences
        val sharedPref: SharedPreferences = getSharedPreferences("hello?", Context.MODE_PRIVATE)
        val thingsList: String? = sharedPref.getString("thingsList", null)

        var thingsForArray = ArrayList<String>()
        if (!thingsList.isNullOrEmpty())
        {
            thingsForArray = ArrayList<String>(thingsList.split(","))
            thingsArray.addAll(thingsForArray)
            thingsAdapter.notifyDataSetChanged()
        }

        list.setOnItemClickListener { adapterView, view, i, l ->
            var selected = thingsAdapter.getItem(i)
            if (list.isItemChecked(i)) {
                if (selected != null) {
                    selectedArray.add(selected)
                }
            }
            else {
                selectedArray.remove(selected)
            }
        }

        addButton.setOnClickListener(View.OnClickListener () {
            var newResponse = ""

            //Нельзя выполнить HTTPS запрос в основной нити
            //Ничего страшного, сделаем новую
            //HTTP вот вообще нельзя нигде и никогда
            Thread {
                val request = Request.Builder()
                    .url("https://techy-api.vercel.app/api/json")
                    .build()

                Log.i("Готов запрос", request.url.toString())

                try {
                    Log.i("Информационный лог", "Пробуем выполнить запрос")
                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            //Обработка неуспешного запроса

                            Log.i("Очень жаль", "Запрос неуспешным оказался")
                            newResponse = "The request was unsuccessful. Really sorry about it"
                        } else {

                            Log.i("Ничего себе", "Запрос прошел")
                            val jsonResponse = response.body!!.string()
                            newResponse = JSONObject(jsonResponse).get("message").toString()
                        }
                    }
                } catch (e: IOException) {

                    Log.e("Грустный лог", "Поймали IOException")
                    //Нету ручек - нет конфетки
                    //Обработка упавшего по причине отсутсвия интернета запроса
                    newResponse = "There's no way sending request without an Internet connection"
                }

                //Нитка в нитке (наверное)
                runOnUiThread {
                    Log.i("Информационный лог", "Мы попали в runOnUiThread")
                    thingsArray.add(newResponse)
                    thingsAdapter.notifyDataSetChanged()
                }

            }.start()
        })

        deleteButton.setOnClickListener(View.OnClickListener () {
            for (i in 0 .. selectedArray.size - 1){
                thingsAdapter.remove(selectedArray.get(i))
            }
            thingsAdapter.notifyDataSetChanged()
            selectedArray.clear()
            list.clearChoices()
        })

    }

    override fun onPause()
    {
        //Сохранение данных в SharedPreferences
        super.onPause()
        Log.i("Информационный лог", "Данные из ListActivity сейчас будут сохраняться")
        val sharedPref: SharedPreferences = getSharedPreferences("hello?", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        val allList: String = TextUtils.join(",", thingsArray)
        editor.putString("thingsList", allList).apply()
        Log.i("Информационный лог", "Данные из ListActivity сохранились")
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("Информационный лог","Создан в методе onStart");
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("Информационный лог","Самый приятный, создан в методе onResume");
    }

    override fun onStop()
    {
        super.onStop()
        Log.i("Информационный лог","Создан в методе onStop");
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.i("Информационный лог","Создан в методе onDestroy");
    }
}