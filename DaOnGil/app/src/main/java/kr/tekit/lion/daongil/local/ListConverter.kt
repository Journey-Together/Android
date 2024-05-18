package kr.tekit.lion.daongil.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class ListConverter {

    @TypeConverter
    fun areaCodeListToJson(value: List<AreaCodeEntity>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToAreaCodeList(value: String): List<AreaCodeEntity> {
        return Gson().fromJson(value, Array<AreaCodeEntity>::class.java).toList()
    }

    @TypeConverter
    fun villageCodeListToJson(value: List<VillageCodeEntity>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToVillageCodeList(value: String): List<VillageCodeEntity> {
        return Gson().fromJson(value, Array<VillageCodeEntity>::class.java).toList()
    }
}