package com.example.deezer.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.deezer.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment=
            supportFragmentManager.findFragmentById(R.id.fcv_main_nav_host) as NavHostFragment
        val navController=navHostFragment.navController
        val navView: BottomNavigationView=
            findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)

    }

}
