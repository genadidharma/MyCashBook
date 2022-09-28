package org.genadidharma.mycashbook.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Cash::class], version = 1)
abstract class MyCashBookDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cashDao(): CashDao
}