package com.waiyanphyo.betternote.data.repositories

import androidx.lifecycle.LiveData
import com.waiyanphyo.betternote.data.NoteWithLabel
import com.waiyanphyo.betternote.data.daos.NoteLabelDao
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteLabelDao: NoteLabelDao
): NoteRepository {
    override suspend fun insertNote(note: Note) = noteLabelDao.insertNote(note)

    override suspend fun insertLabel(label: Label) = noteLabelDao.insertLabel(label)

    override suspend fun getAllLabels() = noteLabelDao.getAllLabels()

    override suspend fun getAllNotes(): LiveData<List<NoteWithLabel>> = noteLabelDao.getNonArchivedNotesWithLabels()
}