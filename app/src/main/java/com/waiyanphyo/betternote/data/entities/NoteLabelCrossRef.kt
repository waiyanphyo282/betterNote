package com.waiyanphyo.betternote.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["noteId", "labelId"])
data class  NoteLabelCrossRef(
    val noteId: Long,
    val labelId: Long
)