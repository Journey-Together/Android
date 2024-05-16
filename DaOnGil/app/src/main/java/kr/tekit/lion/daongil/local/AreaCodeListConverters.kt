package kr.tekit.lion.daongil.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class AreaCodeListConverters {
    @TypeConverter
    fun listToJson(value: List<AreaCodeEntity>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<AreaCodeEntity> {
        return Gson().fromJson(value, Array<AreaCodeEntity>::class.java).toList()
    }
}