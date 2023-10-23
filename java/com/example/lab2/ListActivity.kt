package com.example.lab2

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import java.io.IOException

class ListActivity : Activity(){
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_form)

       // run("https://api.github.com/users/Evin1-/repos")

        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val list = findViewById<ListView>(R.id.listOfThings)

        //val client = OkHttpClient()
        val thingsArray = ArrayList<String>()
        val selectedArray = ArrayList<String>()
        val thingsAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, thingsArray)
        list.adapter = thingsAdapter
        var strNum = 0

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
            strNum++
            thingsArray.add(strNum.toString())
            thingsAdapter.notifyDataSetChanged()
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

    /*fun run(url:String) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call:Call, e:IOException){}
            override fun onResponse(call: Call, response: Response)
            return response.body()?.strign())
        })
    }*/

}