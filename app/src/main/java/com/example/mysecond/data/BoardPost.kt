package com.example.mysecond.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "board_post")
data class BoardPost (
        @PrimaryKey(autoGenerate = true) val uid: Int = 0,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "content") val content: String,
//        @ColumnInfo(name = "created_time" ) val createdTime: Date // How to auto fill ???????
)