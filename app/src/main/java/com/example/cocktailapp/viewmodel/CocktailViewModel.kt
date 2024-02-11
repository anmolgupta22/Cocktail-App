package com.example.cocktailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.model.CocktailModel
import com.example.cocktailapp.model.Drinks
import com.example.cocktailapp.database.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class CocktailViewModel @Inject constructor(private val repository: RoomRepository) : ViewModel() {

    val filterHm: HashMap<String?, Drinks> = hashMapOf()

    suspend fun fetchCocktail(): CocktailModel? {
        val job = viewModelScope.async(Dispatchers.IO) {
            repository.fetchAllCocktail()
        }
        return job.await()
    }

    fun insertCocktail(cocktailModel: CocktailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCocktail(cocktailModel)
        }
    }

}