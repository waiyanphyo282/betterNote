package com.waiyanphyo.betternote.di

import com.waiyanphyo.betternote.data.repositories.NoteRepository
import com.waiyanphyo.betternote.data.repositories.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun provideNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

}