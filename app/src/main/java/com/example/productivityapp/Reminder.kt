package com.example.productivityapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders_table")
data class Reminder(
    @PrimaryKey val requestCode: Int,
    val title: String,
    val date: String,
    val reminderTime: Long
)