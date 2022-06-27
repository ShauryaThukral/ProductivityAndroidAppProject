package com.example.productivityapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context!!,"productivityApp")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(intent!!.getStringExtra("TITLE"))
            .setContentText("Hey you have a reminder!")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(123,builder.build())
    }
}