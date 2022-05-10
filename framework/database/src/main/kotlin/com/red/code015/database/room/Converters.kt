package com.red.code015.database.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

open class Converters {

    private val gson: Gson = Gson()

    @TypeConverter
    fun listIntToString(someObjects: List<Int>): String? = gson.toJson(someObjects)

    @TypeConverter
    fun stringToListInt(data: String?): List<Int> {
        if (data == null) return Collections.emptyList()
        return try {
            gson.fromJson(data, object : TypeToken<List<Int>>() {}.type)
        } catch (e: Exception) {
            Collections.emptyList()
        }
    }

}