package com.buzzingsilently.dhirenparmarassignment.database.converter

import androidx.room.TypeConverter
import com.buzzingsilently.dhirenparmarassignment.model.Owner
import com.google.gson.Gson

//type converter that stores objects as strings and vice versa
//to support storing of objects in local database
class OwnerConverter {

    @TypeConverter
    fun storedStringToOwner(data: String?): Owner? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        return gson.fromJson(data, Owner::class.java)
    }

    @TypeConverter
    fun OwnerToStoredString(myObjects: Owner?): String? {
        return Gson().toJson(myObjects)
    }
}