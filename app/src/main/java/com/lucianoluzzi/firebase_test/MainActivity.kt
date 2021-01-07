package com.lucianoluzzi.firebase_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lucianoluzzi.firebase_test.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addFragment(fragment: Fragment, tag: String? = null) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }
}