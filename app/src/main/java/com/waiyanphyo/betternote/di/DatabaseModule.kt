package com.waiyanphyo.betternote.di

import android.content.Context
import androidx.room.Room
import com.waiyanphyo.betternote.data.NoteDatabase
import com.waiyanphyo.betternote.data.daos.ArchivedNoteDao
import com.waiyanphyo.betternote.data.daos.LabelDao
import com.waiyanphyo.betternote.data.daos.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }

    @Provides
    fun provideArchivedNoteDao(db: NoteDatabase): ArchivedNoteDao {
        return db.archivedNoteDao()
    }
}