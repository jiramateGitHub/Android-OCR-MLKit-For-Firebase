package com.example.ocrmlkitforfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.ocrmlkitforfirebase.Constants.INITIAL_PERMS

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(this, INITIAL_PERMS, Constants.INITIAL_REQUEST)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val load_fragment = LibraryFragment()
        transaction.replace(R.id.contentContainer, load_fragment, "LibraryFragment")
        transaction.addToBackStack("LibraryFragment")
        transaction.commit()
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager.findFragmentById(R.id.contentContainer)
        if (manager is LibraryFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}