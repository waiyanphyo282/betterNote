package com.waiyanphyo.betternote.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class NoteWithLabel(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "label_id",
        entityColumn = "id"
    )
    val label: Label?
)