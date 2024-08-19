package com.waiyanphyo.betternote.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.waiyanphyo.betternote.data.entities.Note
import com.waiyanphyo.betternote.data.entities.NoteWithLabel

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    @Transaction
    @Query("SELECT * FROM notes")
    suspend fun getNotesWithLabels(): List<NoteWithLabel>
}
