package com.waiyanphyo.betternote.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.waiyanphyo.betternote.data.LabelWithNote

@Dao
interface LabelDao {


    @Transaction
    @Query("SELECT * FROM labels")
    fun getLabelsWithNotes(): LiveData<List<LabelWithNote>>
}
