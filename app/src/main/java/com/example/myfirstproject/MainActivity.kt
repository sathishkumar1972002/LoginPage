package com.example.myfirstproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myfirstproject.R.*
import com.example.myfirstproject.databinding.ActivityMainBinding
import com.example.myfirstproject.databinding.ActivitySample3Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

   private lateinit var binding: ActivityMainBinding
   private lateinit var firebaseApp:FirebaseAuth
    private lateinit var user:String
    private lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
       firebaseApp=FirebaseAuth.getInstance()


        setContentView(binding.root)

        if(firebaseApp.currentUser?.uid != null) {
            var Ukey = firebaseApp.currentUser?.uid.toString()
            var currentUser = firebaseApp.currentUser?.email.toString()
            Log.d("sathish","${firebaseApp.currentUser?.uid}")

           Intent(this, LandingPage::class.java).also {
                it.putExtra("Uid", Ukey)
                it.putExtra("Mail", currentUser)
                startActivity(it)
            }
        }

        binding.forgotpass.setOnClickListener {
            
        }

        binding.loginbtn.setOnClickListener {

            var flag=0   //flag to check user and pass is never null
            if (!binding.Usertxt.text.isNullOrEmpty()) {
                 user = binding.Usertxt.text.toString()
                flag++
            }
            else
            {
                binding.Usertxt.error="Username is Required"

            }
            if(!binding.Passtxt.text.isNullOrBlank()) {
                 pass = binding.Passtxt.text.toString()
                flag++
            }
            else
            {
                binding.Passtxt.error="Password is Required"
            }

           if (flag==2){ firebaseApp.signInWithEmailAndPassword(user,pass).addOnCompleteListener {
               if (it.isSuccessful) {
                        var Ukey = firebaseApp.currentUser?.uid.toString()

                   if (firebaseApp.currentUser?.isEmailVerified== true) {
                       Toast.makeText(this, ("Successfully login "), Toast.LENGTH_LONG).show()
                       var intent = Intent(this, LandingPage::class.java).also {
                           it.putExtra("Uid", Ukey)
                           it.putExtra("Mail", user)
                           startActivity(it)
                       }

                       binding.apply {
                           Usertxt.text = null
                           Passtxt.text = null
                       }
                   }
                   else
                   {
                       Toast.makeText(this, ("Email not verified"), Toast.LENGTH_LONG).show()
                   }

               } else {
                   Toast.makeText(this, ("Invalid input"), Toast.LENGTH_LONG).show()

               }
           }

            }







        }

        binding.eye.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.Passtxt.inputType = android.text.InputType.TYPE_CLASS_TEXT
            } else {
               binding.Passtxt.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }



        binding.signuptxt.setOnClickListener{
            val intent= Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }


    }


}




