package com.example.remiderapp

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
        val notification : Notification = NotificationCompat
            .Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .build()
            val manager = context?.let {
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            manager?.let {
                it.notify(notificationID,notification)
            }
        }


    }
}