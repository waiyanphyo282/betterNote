package com.waiyanphyo.betternote.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.waiyanphyo.betternote.data.LabelWithNote
import com.waiyanphyo.betternote.data.NoteWithLabel
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note
import com.waiyanphyo.betternote.data.entities.NoteLabelCrossRef

@Dao
interface NoteLabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteLabelCrossRef(noteLabelCrossRef: NoteLabelCrossRef)

    @Transaction
    @Query("SELECT * FROM notes LEFT JOIN archived_notes ON notes.noteId = archived_notes.note_id WHERE archived_notes.note_id IS NULL")
    fun getNonArchivedNotesWithLabels(): LiveData<List<NoteWithLabel>>

    @Transaction
    @Query("SELECT * FROM labels")
    fun getLabelWithNotes(): LiveData<List<LabelWithNote>>

    // Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    // Label

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: Label): Long

    @Query("SELECT * FROM labels")
    fun getAllLabels(): LiveData<List<Label>>
}