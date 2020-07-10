package com.example.ocrmlkitforfirebase

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.ocrmlkitforfirebase.Constants.INITIAL_PERMS

class MainActivity : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, INITIAL_PERMS, Constants.INITIAL_REQUEST)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentContainer, MenuFragment(), "MenuFragment")
        transaction.addToBackStack("MenuFragment").commit()
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager.findFragmentById(R.id.contentContainer)
        if (manager is MenuFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}