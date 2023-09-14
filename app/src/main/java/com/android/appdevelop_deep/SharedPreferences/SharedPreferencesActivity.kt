package com.android.appdevelop_deep.SharedPreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.databinding.ActivitySharedPreferencesBinding

class SharedPreferencesActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySharedPreferencesBinding.inflate(layoutInflater) }
    private lateinit var spf: SPF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        spf = SPF(this)

        binding.btnSharedpreferenceSave.setOnClickListener {
            val content = binding.sharedpreferenceEditText.text.toString()
            spf.saveData(content)
            finish()
        }
        val loadData = spf.loadData()
        binding.sharedpreferenceEditText.setText(loadData)
    }
}