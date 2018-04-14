package com.azabost.simplemvvm.persistence.dao

import android.arch.persistence.room.*
import com.azabost.simplemvvm.persistence.entities.CommitInfoEntity
import io.reactivex.Flowable

@Dao
interface CommitInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCommitInfo(info: CommitInfoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCommitInfo(info: CommitInfoEntity)

    @Query("SELECT * FROM commit_info")
    fun getAllCommitInfo(): Flowable<List<CommitInfoEntity>>

    @Query("SELECT * FROM commit_info WHERE sha like :sha")
    fun getCommitInfoBySha(sha: String): CommitInfoEntity?
}