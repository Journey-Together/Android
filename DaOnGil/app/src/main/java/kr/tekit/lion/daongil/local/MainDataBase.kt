package kr.tekit.lion.daongil.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(ListConverter::class)
@Database(entities = [AreaCodeEntity::class, VillageCodeEntity::class], version = 1)
abstract class MainDataBase: RoomDatabase() {

    abstract fun areaCodeDao(): AreaCodeDao
    abstract fun villageCodeDao(): VillageCodeDao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase?= null

        fun getDatabase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this){
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