package com.waiyanphyo.betternote.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waiyanphyo.betternote.data.entities.Label

@Dao
interface LabelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label: Label)

    @Query("SELECT * FROM labels")
    suspend fun getAllLabels(): List<Label>
}
