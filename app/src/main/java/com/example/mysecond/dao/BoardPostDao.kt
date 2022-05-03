package com.example.mysecond.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mysecond.data.BoardPost
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardPostDao {

    @Insert
    fun insert(boardPost: BoardPost)

    @Query("SELECT * FROM board_post")
    fun findAll(): Flow<List<BoardPost>>


}