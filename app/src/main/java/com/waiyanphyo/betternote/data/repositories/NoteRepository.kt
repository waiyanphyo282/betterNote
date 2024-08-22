package com.waiyanphyo.betternote.data.repositories

import androidx.lifecycle.LiveData
import com.waiyanphyo.betternote.data.NoteWithLabel
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note

interface NoteRepository {

    suspend fun insertNote(note: Note): Long

    suspend fun insertLabel(label: Label): Long

    suspend fun getAllLabels(): LiveData<List<Label>>

    suspend fun getAllNotes(): LiveData<List<NoteWithLabel>>
}