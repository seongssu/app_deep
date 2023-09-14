package com.android.appdevelop_deep.GetUserLocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.databinding.ActivityGetUserLocationBinding

class GetUserLocationActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGetUserLocationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}