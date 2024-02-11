package com.example.cocktailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailapp.database.RoomRepository
import javax.inject.Inject


class CocktailViewModelFactory @Inject constructor(private val instance: RoomRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RoomRepository::class.java).newInstance(instance)
    }
}