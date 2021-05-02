package com.buzzingsilently.dhirenparmarassignment.database.converter

import androidx.room.TypeConverter
import com.buzzingsilently.dhirenparmarassignment.model.BuiltBy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

//type converter that stores objects as strings and vice versa
//to support storing of objects in local database
class BuiltByTypeConverter {

    @TypeConverter
    fun storedStringToBuiltBy(data: String?): List<BuiltBy?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<BuiltBy?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun builtByToStoredString(myObjects: List<BuiltBy?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}