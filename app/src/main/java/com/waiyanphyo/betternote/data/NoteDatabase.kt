package com.waiyanphyo.betternote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waiyanphyo.betternote.data.daos.ArchivedNoteDao
import com.waiyanphyo.betternote.data.daos.LabelDao
import com.waiyanphyo.betternote.data.daos.NoteDao
import com.waiyanphyo.betternote.data.daos.NoteLabelDao
import com.waiyanphyo.betternote.data.entities.ArchivedNote
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note
import com.waiyanphyo.betternote.data.entities.NoteLabelCrossRef

@Database(entities = [Note::class, Label::class, ArchivedNote::class, NoteLabelCrossRef::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteLabelDao(): NoteLabelDao
    abstract fun archivedNoteDao(): ArchivedNoteDao
}