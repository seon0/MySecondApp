package com.example.mysecond

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mysecond.databinding.ActivityMainBinding
import com.example.mysecond.ui.BoardFragment
import com.example.mysecond.ui.HomeFragment
import com.example.mysecond.ui.MyPageFragment
import com.example.mysecond.ui.PlayFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, homeFragment).commit()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.menu_home, R.id.menu_board, R.id.menu_play, R.id.menu_mypage))
//        navController.setGraph(R.navigation.main_nav_graph)
        binding.bottomNavMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("MAIN", (destination.label.toString() + ", " + destination.id))
        }

        binding.bottomNavMenu.setOnItemSelectedListener { menu ->
            when ( menu.itemId ) {
                R.id.menu_home -> supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, homeFragment).commit()
                R.id.menu_board -> supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, boardFragment).commit()
                R.id.menu_play -> supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, playFragment).commit()
                R.id.menu_mypage -> supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, myPageFragment).commit()
            }
            true
        }
*/



//        binding.bottomNavMenu.background = null


    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when ( item.itemId ) {
//            R.id.menu_home -> supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, homeFragment).commit()
//            R.id.menu_board -> supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, boardFragment).commit()
//            R.id.menu_play -> supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, playFragment).commit()
//            R.id.menu_mypage -> supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, myPageFragment).commit()
//        }
//        return true
//    }



}