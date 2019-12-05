package com.cargamos.cargamosshopper.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.cargamos.cargamosshopper.BuildConfig
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.views.main.MainActivity

// https://www.techotopia.com/index.php/An_Android_8_Notifications_Kotlin_Tutorial

object NotificationsUtils {
    internal var notificationManager: NotificationManager? = null
    internal val PROGRESS_NOTIFICATION = "cargamos_channel_01"
    internal val NEW_SERVICE_NOTIFICATION = "cargamos_channel_01"

    fun showBindedNotification(context: Context): Notification {
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        createNotificationChannel(context, BuildConfig.NOTIFICATIONS_CHANNEL_ID)

        return NotificationCompat.Builder(context, BuildConfig.NOTIFICATIONS_CHANNEL_ID)
            .setContentTitle("Cargamos Shopper")
            .setContentText("Click para abrir")
            .setSmallIcon(R.drawable.ic_logo)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setGroup(BuildConfig.NOTIFICATIONS_FOREGROUND_GROUP)
            .build()
    }

    fun showProgressNotification(context: Context) {
        val notificationID = 102

        createNotificationChannel(context, PROGRESS_NOTIFICATION)

        val notification = NotificationCompat.Builder(context, BuildConfig.NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Subiendo Imagenes")
            .setProgress(0, 0, true)
            .build()

        notificationManager?.notify(notificationID, notification)
    }


    fun sendNotification(context: Context, contentText: String) {
        val notificationID = 101

        createNotificationChannel(context, BuildConfig.NOTIFICATIONS_CHANNEL_ID)

        val notification = NotificationCompat.Builder(context, BuildConfig.NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Cargamos")
            .setContentText(contentText)
            .build()

        notificationManager?.notify(notificationID, notification)
    }

    private fun createNotificationChannel(context: Context, channel: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notificationManager = context.getSystemService(NotificationManager::class.java) as NotificationManager

            val name = "main"
            val description = "main notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channel, name, importance)

            channel.description = description

            notificationManager!!.createNotificationChannel(channel)
        }
    }
}
