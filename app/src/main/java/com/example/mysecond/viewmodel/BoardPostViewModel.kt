package com.example.mysecond.viewmodel

import androidx.lifecycle.*
import com.example.mysecond.R
import com.example.mysecond.dao.BoardPostDao
import com.example.mysecond.data.BoardPost
import com.example.mysecond.data.ProfileCard

class BoardPostViewModel(
    private val boardPostDao: BoardPostDao
) : ViewModel() {

     val allItems: LiveData<List<BoardPost>> = boardPostDao.findAll().asLiveData()

}

class BoardPostViewModelFactory(private val boardPostDao: BoardPostDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if ( modelClass.isAssignableFrom(BoardPostViewModel::class.java) ) {
            return BoardPostViewModel(boardPostDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}