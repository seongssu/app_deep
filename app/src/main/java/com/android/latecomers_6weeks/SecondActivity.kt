package com.android.latecomers_6weeks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.latecomers_6weeks.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private val binding by lazy{ ActivitySecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("6weeks!","Second_onCreate")

        binding.btnFinish.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            this.showToast("FristActivity로 이동")
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("6weeks!","Second_onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("6weeks!","Second_onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("6weeks!", "Second_onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("6weeks!","Second_onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("6weeks!","Second_onDestroy")
    }
}