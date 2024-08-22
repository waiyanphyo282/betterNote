package com.waiyanphyo.betternote

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.waiyanphyo.betternote.data.NoteDatabase
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note
import com.waiyanphyo.betternote.data.NoteWithLabel
import com.waiyanphyo.betternote.data.daos.ArchivedNoteDao
import com.waiyanphyo.betternote.data.daos.NoteLabelDao
import com.waiyanphyo.betternote.data.entities.ArchivedNote
import com.waiyanphyo.betternote.data.entities.NoteLabelCrossRef
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteLabelDaoTest {

    private lateinit var testDatabase: NoteDatabase
    private lateinit var noteLabelDao: NoteLabelDao
    private lateinit var archivedNoteDao: ArchivedNoteDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDatabase = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).allowMainThreadQueries().build()
        noteLabelDao = testDatabase.noteLabelDao()
        archivedNoteDao = testDatabase.archivedNoteDao()
    }

    @After
    fun tearDown() {
        testDatabase.close()
    }

    // Add more test cases as needed
    @Test
    @Throws(Exception::class)
    fun dao_returnInsertedNote() = runTest {
        val note = Note(
            title = "Test Note",
            content = "This is a test note",
            backgroundColor = "#FFFFFF"
        )
        noteLabelDao.insertNote(note)
        val notesWithLabel = noteLabelDao.getNonArchivedNotesWithLabels().getOrAwaitValue()
        assertThat(notesWithLabel.first()).isInstanceOf(NoteWithLabel::class.java)
        assertThat(notesWithLabel.first().labels.size).isEqualTo(0)
    }

    @Test
    @Throws(Exception::class)
    fun dao_archivedNoteIsNotReturned() = runTest {
        // Create and archive a note
        val note = Note(
            title = "Test Note",
            content = "This is a test note",
            backgroundColor = "#FFFFFF"
        )
        val noteId = noteLabelDao.insertNote(note)
        archivedNoteDao.insert(ArchivedNote(noteId = noteId))

        // Verify that no non-archived notes are returned
        val notesWithLabel = noteLabelDao.getNonArchivedNotesWithLabels().getOrAwaitValue()
        assertThat(notesWithLabel).isEmpty()
    }

    @Test
    @Throws(Exception::class)
    fun dao_nonArchivedNoteIsReturned() = runTest {
        // Create and insert a non-archived note
        val testNote = Note(
            title = "Test Note",
            content = "This is a test note",
            backgroundColor = "#FFFFFF"
        )
        val noteId = noteLabelDao.insertNote(testNote)
        archivedNoteDao.insert(ArchivedNote(noteId = noteId))
        val newNote = Note(
            title = "Test Note 2",
            content = "This is a test note 2",
            backgroundColor = "#FFFFFF"
        )
        noteLabelDao.insertNote(newNote)

        // Verify that the new non-archived note is returned
        val notesWithLabel = noteLabelDao.getNonArchivedNotesWithLabels().getOrAwaitValue()
        assertThat(notesWithLabel.size).isEqualTo(1)
        assertThat(notesWithLabel[0].note.title).isEqualTo("Test Note 2")
        assertThat(notesWithLabel[0].note.content).isEqualTo("This is a test note 2")
    }


    @Test
    @Throws(Exception::class)
    fun dao_returnInsertedLabel() = runTest {
        val label = Label(1,"Test Label")
        noteLabelDao.insertLabel(label)
        val labels = noteLabelDao.getAllLabels().getOrAwaitValue()
        assertThat(labels.first()).isEqualTo(label)
    }

    @Test
    @Throws(Exception::class)
    fun dao_returnsLabelWithNote_whenInsertedWithLabel() = runTest {
        val noteOne = Note(
            title = "Test Note",
            content = "This is a test note",
            backgroundColor = "#FFFFFF")
        val noteTwo = Note(
            title = "Test Note 2",
            content = "This is a test note 2",
            backgroundColor = "#FFFFFF")
        val label = Label(1,"Test Label")
        val labelId = noteLabelDao.insertLabel(label)
        val noteOneId = noteLabelDao.insertNote(noteOne)
        val noteTwoId = noteLabelDao.insertNote(noteTwo)
        noteLabelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteOneId, labelId))
        noteLabelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteTwoId, labelId))
        val labelWithNotes = noteLabelDao.getLabelWithNotes().getOrAwaitValue()
        assertThat(labelWithNotes.first().label).isEqualTo(label)
        assertThat(labelWithNotes.first().notes.size).isEqualTo(2)
    }

    @Test
    @Throws(Exception::class)
    fun dao_returnsNoteWithLabel_including_label_whenInsertedWithLabel() = runTest {
        val note = Note(
            title = "Test Note",
            content = "This is a test note",
            backgroundColor = "#FFFFFF")
        val label = Label(1,"Test Label")
        val noteId = noteLabelDao.insertNote(note)
        val labelId = noteLabelDao.insertLabel(label)
        noteLabelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteId, labelId))
        val notesWithLabel = noteLabelDao.getNonArchivedNotesWithLabels().getOrAwaitValue()
        Log.d("NoteDaoTest", "labels: ${notesWithLabel.first().labels}")
        assertThat(notesWithLabel.first()).isInstanceOf(NoteWithLabel::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun dao_returnsNoteWithTwoLabels_whenInsertedWithTwoLabels() = runTest {
        val note = Note(
            title = "Test Note",
            content = "This is a test note",
            backgroundColor = "#FFFFFF")
        val label1 = Label(1,"Test Label 1")
        val label2 = Label(2,"Test Label 2")
        val noteId = noteLabelDao.insertNote(note)
        val label1Id = noteLabelDao.insertLabel(label1)
        val label2Id = noteLabelDao.insertLabel(label2)
        noteLabelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteId, label1Id))
        noteLabelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteId, label2Id))
        val notesWithLabel = noteLabelDao.getNonArchivedNotesWithLabels().getOrAwaitValue()
        assertThat(notesWithLabel.first().labels.size).isEqualTo(2)
    }
}