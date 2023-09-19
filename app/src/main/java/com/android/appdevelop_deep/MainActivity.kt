package com.android.appdevelop_deep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.CreatingGoogleMapsApp.CreatingGoogleMapsAppActivity
import com.android.appdevelop_deep.Miseya.MiseyaActivity
import com.android.appdevelop_deep.SharedPreferences.SharedPreferencesActivity
import com.android.appdevelop_deep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnSharedpreference.setOnClickListener {
                val intent = Intent(this@MainActivity, SharedPreferencesActivity::class.java )
                startActivity(intent)
            }
            btnGooglemaps.setOnClickListener {
                val intent = Intent(this@MainActivity, CreatingGoogleMapsAppActivity::class.java)
                startActivity(intent)
            }
            btnMiseya.setOnClickListener {
                val intent = Intent(this@MainActivity, MiseyaActivity::class.java)
                startActivity(intent)
            }
        }


    }
}