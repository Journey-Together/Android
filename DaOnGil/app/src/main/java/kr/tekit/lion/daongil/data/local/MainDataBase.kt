package kr.tekit.lion.daongil.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.tekit.lion.daongil.data.local.dao.AreaCodeDao
import kr.tekit.lion.daongil.data.local.dao.RecentSearchKeywordDao
import kr.tekit.lion.daongil.data.local.dao.SigunguCodeDao
import kr.tekit.lion.daongil.data.dto.local.AreaCodeEntity
import kr.tekit.lion.daongil.data.dto.local.RecentSearchKeywordEntity
import kr.tekit.lion.daongil.data.dto.local.SigunguCodeEntity

@TypeConverters(ListConverter::class)
@Database(
    entities = [AreaCodeEntity::class, SigunguCodeEntity::class, RecentSearchKeywordEntity::class],
    version = 3
)
abstract class MainDataBase : RoomDatabase() {

    abstract fun areaCodeDao(): AreaCodeDao
    abstract fun sigunguCodeDao(): SigunguCodeDao
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