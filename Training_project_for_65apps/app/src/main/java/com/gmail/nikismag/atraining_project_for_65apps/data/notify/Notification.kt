package com.gmail.nikismag.atraining_project_for_65apps.data.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.presentation.MainActivity

fun NotificationManager.sendNotification(context: Context, userId: Long, message: String) {

    createNotificationChannel(context)

    val pendingIntent = PendingIntent.getActivity(
        context,
        userId.toInt(),
        MainActivity.newDetailIntent(context, userId),
        getIntentFlags()
    )

    val notification = NotificationCompat.Builder(context.applicationContext, CHANNEL_ID)
        .setContentTitle("Birthday")
        .setSmallIcon(R.drawable.ic_birthday)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setChannelId(CHANNEL_ID)
        .setContentText(message)
        .build()

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(NOTIFICATION_ID, notification)
}

private fun NotificationManager.createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID, context.getString(R.string.notifications_channel_description),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor = Color.GREEN
            enableLights(true)
            description = context.getString(R.string.notifications_channel_description)
        }
        createNotificationChannel(channel)
    }
}

fun getIntentFlags(flag: Int = PendingIntent.FLAG_UPDATE_CURRENT) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE or flag
    } else {
        flag
    }

private const val CHANNEL_ID = "channelID"
private const val NOTIFICATION_ID = 0
