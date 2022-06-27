package com.example.productivityapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        val notesFragment = NotesFragment()
        val reminderFragment = ReminderFragment()
        val boredFragment = BoredFragment()

        setFragment(notesFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.notes -> setFragment(notesFragment)
                R.id.reminders -> setFragment(reminderFragment)
                R.id.bored -> setFragment(boredFragment)
            }
            true
        }
    }

    private fun setFragment(currentFragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHost,currentFragment)
            .commit()
    }

    fun setReminder(requestCode: Int, title: String, date: String){
        val setReminderFragment = SetReminderFragment.newInstance(requestCode,title,date)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.pop_slide_in,
                R.anim.pop_slide_out
            )
            .replace(R.id.fragmentHost,setReminderFragment)
            .addToBackStack(null)
            .commit()
    }

    fun createNotes(createdAt: Long, title: String, content: String){
        val createNotesFragment = CreateNoteFragment.newInstance(createdAt,title,content)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.pop_slide_in,
                R.anim.pop_slide_out
            )
            .replace(R.id.fragmentHost,createNotesFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "ProductivityReminderChannel"
            val descriptionText = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("productivityApp", name, importance)
            mChannel.description = descriptionText

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}