package com.example.mysecond

import android.app.Application
import com.example.mysecond.database.AppBoardDatabase

class BaseApplication : Application() {

    val boardDatabase : AppBoardDatabase by lazy { AppBoardDatabase.getDatabase(this) }

}