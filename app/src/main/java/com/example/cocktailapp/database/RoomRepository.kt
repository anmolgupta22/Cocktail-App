package com.example.cocktailapp.database

import com.example.cocktailapp.model.CocktailModel
import com.example.cocktailapp.database.dao.CocktailDao
import javax.inject.Inject

open class RoomRepository @Inject constructor(
    private val cocktailDao: CocktailDao,
) {

    suspend fun fetchAllCocktail(): CocktailModel? {
        return cocktailDao.fetchAllCocktail()
    }

    fun insertCocktail(cocktailModel: CocktailModel) {
        cocktailDao.insert(cocktailModel)
    }

}