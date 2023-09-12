package com.android.latecomers_6weeks

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Log.d("6weeks","onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("6weeks","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("6weeks","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("6weeks", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("6weeks","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("6weeks","onDestroy")
    }
}