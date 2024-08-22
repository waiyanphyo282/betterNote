package com.waiyanphyo.betternote.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waiyanphyo.betternote.data.entities.ArchivedNote

@Dao
interface ArchivedNoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(archivedNote: ArchivedNote)

    @Query("SELECT * FROM archived_notes")
    fun getAllArchivedNotes(): LiveData<List<ArchivedNote>>

    @Query("SELECT * FROM archived_notes WHERE note_id = :noteId")
    suspend fun getArchivedNoteById(noteId: Int): ArchivedNote?
}
