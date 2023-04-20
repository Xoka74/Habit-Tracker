package com.example.hometask3

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.hometask3.databinding.ActivityMainBinding
import data.HabitDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        val db = HabitDatabase.getInstance(this)
    }

    private fun setupNavigation() {
        navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment)
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.sideBarNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, binding.drawerLayout)
}

