package com.example.mysecond

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mysecond.databinding.ActivityMenuBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var appBarConfiguration : AppBarConfiguration
//    private lateinit var contextButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_bottom_menu) //binding.navBottomMenu

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_board, R.id.navigation_play, R.id.navigation_mypage
            ),
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()

//        contextButton =binding.layoutConstraint.findViewById<FloatingActionButton>(R.id.menu_write)
//        // + 글쓰기 버튼  컨텍스트 메뉴 등록
//        registerForContextMenu(contextButton)

            binding.layoutConstraint.findViewById<FloatingActionButton>(R.id.menu_write).setOnClickListener { view ->
            Log.d("MenuActivity", "Click write button!!!")

//            // TODO 사진 / 글  선택 옵션 띄우기
            val popup = PopupMenu(this , view)
            menuInflater.inflate(R.menu.write_popup_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when ( menuItem.itemId ) {
                    R.id.write_context_menu_post -> {
                        Log.d("MenuActivity","make post click")

                        // 글 입력창 띄우기
                        val writeIntent = Intent(this,  WritePostActivity::class.java)
                        startActivity(writeIntent)
                    }
                    R.id.write_context_menu_photo -> {
                        Log.d("MenuActivity","make photo click")

                        // 글 입력창 띄우기
                        val writeIntent = Intent(this,  WritePostActivity::class.java)
                        startActivity(writeIntent)
                    }
                }

                return@setOnMenuItemClickListener(true)
            }
            popup.show()

        }


    }

//    override fun onCreateContextMenu(
//        menu: ContextMenu?,
//        v: View?,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//
//        if  (v === contextButton ) {
////            menu!!.setHeaderTitle("선택지")
//            menuInflater.inflate(R.menu.write_context_menu, menu)
//        }
//
//
//    }
//
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.write_context_menu_photo -> {
//                Log.d("MenuActivity", "context menu selected : PHOTO")
//                return true
//            }
//            R.id.write_context_menu_post -> {
//                Log.d("MenuActivity", "context menu selected : POST")
//                return true
//            }
//        }
//        return false
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
//        return true // super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(_appBarConfiguration) || super.onSupportNavigateUp()
//    }

}