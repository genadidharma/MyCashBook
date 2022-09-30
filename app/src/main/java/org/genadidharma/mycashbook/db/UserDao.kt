package org.genadidharma.mycashbook.db

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun checkUser(username: String): Boolean

    @Insert
    fun addUser(user: User)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND password = :password)")
    fun login(username: String, password: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM user WHERE password = :password)")
    fun checkPassword(password: String): Boolean

    @Query("UPDATE user SET password = :newPassword WHERE password = :currentPassword")
    fun changePassword(currentPassword: String, newPassword: String)
}