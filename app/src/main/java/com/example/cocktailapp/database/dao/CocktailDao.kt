package com.example.cocktailapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.cocktailapp.model.CocktailModel


@Dao
interface CocktailDao : BaseDao<CocktailModel> {

    @Query("Select * from tbl_cocktail")
    suspend fun fetchAllCocktail(): CocktailModel?
}