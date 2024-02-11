package com.example.cocktailapp.database

import androidx.room.TypeConverter
import com.example.cocktailapp.model.Drinks
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun toResultsList(list: String?): ArrayList<Drinks?>? {
        val typeToken: Type = object : TypeToken<ArrayList<Drinks?>?>() {}.type
        return Gson().fromJson<ArrayList<Drinks?>>(list, typeToken)
    }

    @TypeConverter
    fun fromResultsList(list: ArrayList<Drinks?>?): String? {
        return Gson().toJson(list)
    }
}