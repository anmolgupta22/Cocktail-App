package com.example.cocktailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Update


@Dao
interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    fun insertAll(cocktailModel: List<T>)

    @Insert(onConflict = REPLACE)
    fun insert(cocktailModel: T): Long

    @Update(onConflict = REPLACE)
    fun update(cocktailModel: T): Int

    @Delete
    fun delete(cocktailModel: T): Int
}