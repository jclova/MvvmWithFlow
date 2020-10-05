package com.joshua.mvvmwithflow.data.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joshua.mvvmwithflow.data.model.UserObject

@Database(
    entities = [
        UserObject::class], version = 1, exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao(): MyDao

    companion object {
        private const val DB_NAME = "myDatabase"
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room
                            .databaseBuilder(
                                context.applicationContext, MyDatabase::class.java,
                                DB_NAME
                            )
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}