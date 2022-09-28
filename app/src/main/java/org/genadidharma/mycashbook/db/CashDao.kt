package org.genadidharma.mycashbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CashDao {
    @Query("SELECT * FROM cash ORDER BY id DESC")
    fun getAllCashes(): List<Cash>

    @Insert
    fun insert(vararg cash: Cash)
}