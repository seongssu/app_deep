package com.android.appdevelop_deep.CreatingGoogleMapsApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.databinding.ActivityCreatingGoogleMapAppBinding

class CreatingGoogleMapsAppActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreatingGoogleMapAppBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}