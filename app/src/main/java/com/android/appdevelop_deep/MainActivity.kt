package com.android.appdevelop_deep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.CreatingGoogleMapsApp.CreatingGoogleMapsAppActivity
import com.android.appdevelop_deep.SharedPreferences.SharedPreferencesActivity
import com.android.appdevelop_deep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSharedpreference.setOnClickListener {
            val intent = Intent(this, SharedPreferencesActivity::class.java )
            startActivity(intent)
        }
        binding.btnGooglemaps.setOnClickListener {
            val intent = Intent(this, CreatingGoogleMapsAppActivity::class.java)
            startActivity(intent)
        }
    }
}