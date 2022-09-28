package org.genadidharma.mycashbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [User::class, Cash::class], version = 1)
abstract class MyCashBookDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cashDao(): CashDao

    companion object {
        private var INSTANCE: MyCashBookDatabase? = null
        @InternalCoroutinesApi
        fun getDatabase(context: Context): MyCashBookDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,MyCashBookDatabase::class.java, "mycashbook_db")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}