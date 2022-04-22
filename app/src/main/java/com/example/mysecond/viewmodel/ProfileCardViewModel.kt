package com.example.mysecond.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysecond.R
import com.example.mysecond.data.ProfileCard

class ProfileCardViewModel : ViewModel() {

    private val _list = MutableLiveData<List<ProfileCard>>()
    val list : LiveData<List<ProfileCard>> = _list

    init {
        _list.value = listOf<ProfileCard>(
            ProfileCard("고은님", 21, "Seoul", R.drawable.profile4),
            ProfileCard("김고은", 31, "Busan", R.drawable.profile3),
            ProfileCard("고은이", 41, "Paju", R.drawable.profile2),
            ProfileCard("고은씨", 23, "Jeju", R.drawable.profile1),
        )
    }


}