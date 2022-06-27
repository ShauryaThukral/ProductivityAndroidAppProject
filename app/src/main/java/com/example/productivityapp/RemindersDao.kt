package com.example.productivityapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RemindersDao {
    @Query("SELECT * FROM reminders_table ORDER BY requestCode ASC")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)
}