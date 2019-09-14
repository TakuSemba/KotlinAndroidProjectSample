package com.example.sampleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {

    companion object {

        var viewModelFactory: ViewModelFactory? = null

        fun get() = if (viewModelFactory == null) {
            ViewModelFactory().also { factory -> viewModelFactory = factory }
        } else {
            checkNotNull(viewModelFactory)
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            UserViewModel::class.java -> UserViewModel(UserRepository()) as T
            else -> throw IllegalArgumentException("unknown ViewModel class $modelClass")
        }
    }
}