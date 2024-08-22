package com.waiyanphyo.betternote.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labels")
data class Label(
    @PrimaryKey(autoGenerate = true) val labelId: Long = 0,
    @ColumnInfo(name = "name") val name: String
)