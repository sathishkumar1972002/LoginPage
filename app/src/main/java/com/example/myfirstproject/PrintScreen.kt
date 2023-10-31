package com.example.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myfirstproject.databinding.ActivityPrintScreenBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class PrintScreen : AppCompatActivity() {
    private lateinit var bind1: ActivityPrintScreenBinding
    private var firebaseApp = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind1 = ActivityPrintScreenBinding.inflate(layoutInflater)
        setContentView(bind1.root)


        val name = intent.getStringExtra("Ext_name")
        val phn = intent.getStringExtra("Ext_phn")
        val mail = intent.getStringExtra("Ext_mail")
        val final: String = " Name is ${name} phone num is $phn & mail Id is $mail"
        Toast.makeText(this, "$final", Toast.LENGTH_SHORT).show()
        bind1.textView4.text = final


    }
}