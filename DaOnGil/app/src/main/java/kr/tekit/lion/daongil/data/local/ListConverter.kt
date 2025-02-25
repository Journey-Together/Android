package kr.tekit.lion.daongil.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import kr.tekit.lion.daongil.data.dto.local.AreaCodeEntity
import kr.tekit.lion.daongil.data.dto.local.SigunguCodeEntity

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
    fun villageCodeListToJson(value: List<SigunguCodeEntity>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToVillageCodeList(value: String): List<SigunguCodeEntity> {
        return Gson().fromJson(value, Array<SigunguCodeEntity>::class.java).toList()
    }
}