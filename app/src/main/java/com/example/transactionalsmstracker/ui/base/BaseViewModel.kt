package com.example.transactionalsmstracker.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.transactionalsmstracker.utils.Resource

class BaseViewModel : ViewModel(){


    val messageStringId = MutableLiveData<Resource<Int>>()
    val messageString = MutableLiveData<Resource<String>>()
}