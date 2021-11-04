package com.gmail.nikismag.atraining_project_for_65apps.data.notify

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.util.*

private const val REMINDER_ACTION =
    "com.gmail.nikismag.atraining_project_for_65apps.data.notify.REMINDER_ACTION"

class ReminderReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val userId = intent.getLongExtra(ARG_USER_ID, -1)
        val message = intent.getStringExtra(ARG_MESSAGE)
        if (userId.toInt() == -1 || message.isNullOrEmpty()) return

        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.sendNotification(context, userId, message)

        val reminderIntent =
            PendingIntent.getBroadcast(context.applicationContext, userId.toInt(), intent, getIntentFlags())
        var reminderDate = LocalDate.now()

        reminderDate =
            reminderDate.plusYears(if (reminderDate.month == Month.FEBRUARY && reminderDate.dayOfMonth == 29) 4 else 1)
        (context.applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.set(
            AlarmManager.RTC_WAKEUP,
            reminderDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
            reminderIntent
        )
    }

    companion object {
        private const val ARG_USER_ID = "args:notification_user_id"
        private const val ARG_MESSAGE = "args:notification_message"

        fun newIntent(context: Context, userId: Long, message: String, user: User) =
            Intent(context, ReminderReceiver::class.java).apply {
                putExtra(ARG_USER_ID, userId)
                putExtra(ARG_MESSAGE, message)
                action = REMINDER_ACTION
            }
    }
}