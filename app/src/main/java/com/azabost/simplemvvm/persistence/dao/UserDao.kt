package com.azabost.simplemvvm.persistence.dao

import android.arch.persistence.room.*
import com.azabost.simplemvvm.persistence.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long): UserEntity?
}