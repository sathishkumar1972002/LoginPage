package com.example.myfirstproject

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.AutoCompleteTextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PermissionResult
import androidx.core.widget.addTextChangedListener
import com.example.myfirstproject.R.drawable.baseline_done_24
import com.example.myfirstproject.databinding.ActivityMain2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity2 : AppCompatActivity() {

   private lateinit var bind: ActivityMain2Binding


    private lateinit var firebaseApp:FirebaseAuth   //this for login Authentication

      var firebas =FirebaseDatabase.getInstance().reference // get instance of Realtime Database
    var count =0  // for validation method

    lateinit var name:String
    lateinit var Username:String
    lateinit var phn:String
    lateinit var mId:String
    lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseApp=FirebaseAuth.getInstance()  // Instance for Firebase Authentication

        bind = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.signUpBtn.setOnClickListener{

             count =0;

            if (validation()==5) {

                // Assuming "objects" is the name of your collection in the database

             firebaseApp.createUserWithEmailAndPassword(mId,pass).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        Toast.makeText(this, ("Successfully created"), Toast.LENGTH_SHORT).show()
                        //////////////////////////////
                        ///////////////////////// Email verificatin check
                        firebaseApp.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                            if (it.isSuccessful)
                                Toast.makeText(this, ("Email verification sent"), Toast.LENGTH_LONG).show()
                        }
                        ///////////////////////////
                        val objectsRef = firebas.child("Users")

                        var customKey = firebaseApp.uid.toString()
                        // Push a new object to the database
                        val newObjectRef = objectsRef.child(customKey)
                       newObjectRef.setValue(UserData(name,Username,phn,mId,pass))

                            .addOnSuccessListener {
                                Toast.makeText(this,"Now login with Mail",Toast.LENGTH_LONG).show()

                                bind.apply {
                                    Fname.text = null
                                    Lname.text = null
                                    Uname.text = null
                                    phone.text = null
                                    mail.text = null
                                    pass1.text = null
                                    confirmPass.text = null
                                }
                                  intent.putExtra("Extra_mail_login",mId)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                            }
                    }
                    else{
                        Toast.makeText(this, ("Already exists"), Toast.LENGTH_SHORT).show()

                    }
                }



                reqpermission()
            }
          }


          bind.SignInBtn.setOnClickListener {
              finish()
              }


//        bind.button.setOnClickListener {
//            var intent = Intent(this,Sample3::class.java)
//
//            startActivity(intent)
//        }


    }

    fun validation():Int
    {


          if (!bind.Fname.text.trim().isNullOrEmpty() ) {
              name = (bind.Fname.text.trim().toString() + " " + bind.Lname.text.trim().toString())
              count++
          }
          else
          {
              bind.Fname.error="First name Required"
          }



        if (!bind.Uname.text.trim().isNullOrEmpty())
        {
             Username = bind.Uname.text.trim().toString()
            count++
        }
        else{
            bind.Uname.error="Username Required"
        }

        if (bind.phone.text.trim().toString().length==10){
            phn = bind.phone.text.trim().toString()
            count++
        }
        else{
            bind.phone.error="Invalid number"

        }


        if (Patterns.EMAIL_ADDRESS.matcher(bind.mail.text.trim()).matches()) {
            mId = bind.mail.text.trim().toString()
            count++
        }
        else{
            bind.mail.error="Invalid Mail"
        }


        if(!bind.pass1.text.isNullOrEmpty()) {     // Password should not be empty
            if (bind.pass1.text.toString().length>=8) {    // Minimun 8 characters required
                if (bind.pass1.text.toString() == bind.confirmPass.text.toString()) {
                     pass = bind.pass1.text.toString()
                    count++
                } else {
                    bind.confirmPass.error = "Password Mismatch"

                }
            }
            else
            {
                bind.pass1.error = "Minimum 8 characters Required"
            }
        }
        else
        {
            bind.pass1.error = "Password required"
        }

        return count
    }


    fun reqpermission()
    {
        var permission = mutableListOf<String>()
        if (!externalstorage())
        {
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!location())
        {
            permission.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (!bluetooth())
        {
            permission.add(Manifest.permission.BLUETOOTH)
        }

        if(permission.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this,permission.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==0 && grantResults.isNotEmpty())
        {
            for (i in grantResults.indices)
            {
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("Permission Reques","Manifest ${permissions[i]} granted")
                }
            }
        }
    }

    private fun externalstorage()=
        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
    private fun location()=
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED
    private fun bluetooth()=
        ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH)==PackageManager.PERMISSION_GRANTED
}