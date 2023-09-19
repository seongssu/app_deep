package com.android.appdevelop_deep.Miseya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.databinding.ActivityMiseyaBinding

class MiseyaActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMiseyaBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}