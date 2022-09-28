package org.genadidharma.mycashbook.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cash(
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "money") val money: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "isIncome") val isIncome: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
