package com.android.latecomers_6weeks

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.latecomers_6weeks.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFirstBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("6weeks","First_onCreate")
        setContentView(binding.root)

        binding.btnToSecondActivity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("6weeks!","First_onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("6weeks!","First_onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("6weeks!", "First_onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("6weeks!","First_onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("6weeks!","First_onDestroy")
    }
}