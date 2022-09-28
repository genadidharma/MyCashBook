package org.genadidharma.mycashbook.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND password = :password)")
    fun login(username: String, password: String)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE password = :password)")
    fun checkPassword(password: String)

    @Query("UPDATE user SET password = :newPassword WHERE password = :currentPassword")
    fun changePassword(currentPassword: String, newPassword: String)
}