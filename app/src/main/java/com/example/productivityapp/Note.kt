package com.example.productivityapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey
    val createdAt: Long,
    val title:String,
    val content: String
)