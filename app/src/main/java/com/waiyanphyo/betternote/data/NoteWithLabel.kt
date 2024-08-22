package com.waiyanphyo.betternote.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note
import com.waiyanphyo.betternote.data.entities.NoteLabelCrossRef

data class NoteWithLabel(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "labelId",
        associateBy = Junction(NoteLabelCrossRef::class)
    )
    val labels: List<Label>
)