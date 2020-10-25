package me.dicoding.bajp.reel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.dicoding.bajp.reel.databinding.ActivityMainBinding
import me.dicoding.bajp.reel.utils.ext.viewBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}