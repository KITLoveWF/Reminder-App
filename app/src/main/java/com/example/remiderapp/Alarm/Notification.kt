package com.example.remiderapp.Alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.remiderapp.R

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = ""
const val messageExtra = ""
class Alarm: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        if (context != null && intent != null) {
//        val notification : Alarm = NotificationCompat
//            .Builder(context, channelID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(intent.getStringExtra(titleExtra))
//            .setContent(intent.getStringExtra(messageExtra))
//            .build()
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationID,notification)


    }
}