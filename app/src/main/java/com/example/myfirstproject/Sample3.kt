package com.example.myfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.myfirstproject.databinding.ActivitySample3Binding
import com.example.myfirstproject.databinding.CustomToastBinding
import java.time.Duration

class Sample3 : AppCompatActivity() {
    private lateinit var binding:ActivitySample3Binding
    private lateinit var bind1: CustomToastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySample3Binding.inflate(layoutInflater)
         bind1= CustomToastBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_sample3)





            setContentView(binding.root)

            var count = 0

            Toast.makeText(this,"Entered",Toast.LENGTH_SHORT).show()

            binding.butnadd.setOnClickListener {

                Toast.makeText(this,"Now counted",Toast.LENGTH_SHORT).show()

                count+=1
                var n1 = binding.textcount1.text.toString().toInt()
                var n2 = binding.textcount2.text.toString().toInt()
                var result =  n1+n2
                binding.res.text=result.toString()
            }

          binding.imgbtn.setOnClickListener {
              binding.imageView.setImageResource(R.drawable.logo )
          }

        binding.rgbtn.setOnClickListener {
            var check= binding.check.isChecked
            if (check) {
                Toast(this).apply{
                duration= Toast.LENGTH_LONG
                    view= layoutInflater.inflate(R.layout.custom_toast,bind1.toast1)
                       show()
                }
                var choice = binding.rgtxt.checkedRadioButtonId
                var select = findViewById<RadioButton>(choice)
                binding.res1.text = select.text
            }
        }

        }
    }
