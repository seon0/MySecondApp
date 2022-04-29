package com.example.mysecond.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mysecond.data.BoardPost

@Dao
interface BoardPostDao {

    @Insert
    fun insert(boardPost: BoardPost)


}