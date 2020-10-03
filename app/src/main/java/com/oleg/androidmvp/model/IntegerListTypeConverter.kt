package com.oleg.androidmvp.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListTypeConverter {
    @TypeConverter
    fun stringToIntegerList(string: String?): MutableList<Int> {
        if (string == null || string.isEmpty() || string == "null") {
            return mutableListOf()
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun integerListToString(list: List<Int>?): String {
        return Gson().toJson(list)
    }
}