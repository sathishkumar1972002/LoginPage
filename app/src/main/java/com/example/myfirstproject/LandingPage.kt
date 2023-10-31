package com.example.myfirstproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myfirstproject.databinding.ActivityLandingPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots

class LandingPage : AppCompatActivity() {
    private lateinit var bind: ActivityLandingPageBinding
    lateinit var userData: UserData
    var database = FirebaseDatabase.getInstance().reference
    var objectRef = database.child("Users")


    var dataAuth = FirebaseAuth.getInstance()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(bind.root)


        var customKey = intent.getStringExtra("Uid").toString()


        val objectRef = objectRef.child(customKey)






           objectRef.addValueEventListener(object :ValueEventListener
            {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("sathis", "onDataChange called")
                    Log.d("sathis", "onDataChange called ${dataSnapshot.value}")

                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val mail = dataSnapshot.child("mail").getValue(String::class.java)
                    val phone = dataSnapshot.child("phone").getValue(String::class.java)
                    val userName = dataSnapshot.child("userName").getValue(String::class.java)
                    val pass = dataSnapshot.child("pass").getValue(String::class.java)


                    
                    Log.d("sathis", "onDataChange called value ${name}")
                    if (name != null && mail != null && phone != null && userName != null && pass!=null) {
                        userData = UserData(name,userName,mail,phone,pass)

                    }
                      //  var retrievedObject= dataSnapshot.getValue(UserData::class.java)

//                        if (retrievedObject != null) {
//                            // Do something with retrievedObject
//                            Log.d("sathis", "onDataChange called value ${retrievedObject.name}")
                         bind.nameset.setText(userData.name)
//                            Toast.makeText(this@LandingPage, "value Entered", Toast.LENGTH_SHORT)
//                                .show()
//                        } else {
//                            Log.d("sathis", "onDataChange called Null")
//                        }

                    Log.d("sathis", "onDataChange exit")

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("sathis", "onCancelled")
                }
            })



        bind.signout.setOnClickListener {
          dataAuth.signOut()
           finish()
        }
    }
}