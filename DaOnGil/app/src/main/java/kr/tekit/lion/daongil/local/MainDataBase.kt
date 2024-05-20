package kr.tekit.lion.daongil.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.tekit.lion.daongil.local.dao.AreaCodeDao
import kr.tekit.lion.daongil.local.dao.RecentSearchKeywordDao
import kr.tekit.lion.daongil.local.dao.VillageCodeDao
import kr.tekit.lion.daongil.local.entity.AreaCodeEntity
import kr.tekit.lion.daongil.local.entity.RecentSearchKeywordEntity
import kr.tekit.lion.daongil.local.entity.VillageCodeEntity

@TypeConverters(ListConverter::class)
@Database(
    entities = [AreaCodeEntity::class, VillageCodeEntity::class, RecentSearchKeywordEntity::class],
    version = 2
)
abstract class MainDataBase : RoomDatabase() {

    abstract fun areaCodeDao(): AreaCodeDao
    abstract fun villageCodeDao(): VillageCodeDao
    abstract fun RecentSearchKeywordDao(): RecentSearchKeywordDao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null

        fun getDatabase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "main_database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}