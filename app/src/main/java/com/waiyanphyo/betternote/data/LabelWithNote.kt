package com.waiyanphyo.betternote.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.waiyanphyo.betternote.data.entities.Label
import com.waiyanphyo.betternote.data.entities.Note
import com.waiyanphyo.betternote.data.entities.NoteLabelCrossRef

data class LabelWithNote(
    @Embedded val label: Label,
    @Relation(
        parentColumn = "labelId",
        entityColumn = "noteId",
        associateBy = Junction(NoteLabelCrossRef::class)
    )
    val notes: List<Note>
)