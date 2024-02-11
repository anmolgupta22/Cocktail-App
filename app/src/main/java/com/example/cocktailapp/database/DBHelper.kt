package com.example.cocktailapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cocktailapp.model.CocktailModel
import com.example.cocktailapp.database.dao.CocktailDao


@Database(
    entities = [CocktailModel::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class DBHelper : RoomDatabase() {

    abstract fun characterDao(): CocktailDao

    companion object {

        private var appDataBaseInstance: DBHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBHelper {
            if (appDataBaseInstance == null) {
                appDataBaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    DBHelper::class.java,
                    "cocktail_database"
                )
                    .build()
            }
            return appDataBaseInstance!!
        }
    }
}