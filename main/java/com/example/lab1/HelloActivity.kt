package com.example.lab1

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class HelloActivity : Activity(), View.OnClickListener {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hellolayout)

        val buttonHello = findViewById<Button>(R.id.buttonHello)
        val textView1 = findViewById<TextView>(R.id.textViewCount1)

        val buttonBye = findViewById<Button>(R.id.buttonBye)
        val textView2 = findViewById<TextView>(R.id.textViewCount2)

        val buttonNull = findViewById<Button>(R.id.buttonNull)

        buttonHello.setOnClickListener(View.OnClickListener () {
            val cur = textView1.text.toString()
            var countCur: Int = Integer.parseInt(cur)
            countCur++

            textView1.text = countCur.toString()
            }
        )

        buttonBye.setOnClickListener(View.OnClickListener () {
            val cur = textView2.text.toString()
            var countCur: Int = Integer.parseInt(cur)
            countCur++

            textView2.text = countCur.toString()
        }
        )

        buttonNull.setOnClickListener(View.OnClickListener () {
            textView1.text = "0"
            textView2.text = "0"
        }
        )
    }

    override fun onClick(p0: View?) {

    }


}