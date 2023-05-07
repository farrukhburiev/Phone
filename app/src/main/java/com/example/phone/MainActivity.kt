package com.example.phone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.phone.databinding.ActivityMainBinding
import com.example.phone.ui.ContactsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.main_activity, ContactsFragment()).commit()
    }
}