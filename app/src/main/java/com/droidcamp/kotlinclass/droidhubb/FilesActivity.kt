package com.droidcamp.kotlinclass.droidhubb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.droidcamp.kotlinclass.droidhubb.databinding.ActivityFilesBinding

class FilesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DroidHubb)
        binding = ActivityFilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}