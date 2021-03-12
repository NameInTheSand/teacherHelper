package com.example.teacherhelper.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import com.example.teacherhelper.utils.Constants
import io.karn.notify.Notify
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val groupName = intent.getStringExtra(Constants.EXTRA_EXACT_ALARM_TITLE)
        when (intent.action) {
            Constants.ACTION_SET_EXACT -> {
                buildNotification(context, "Нагадування про пару", "У Вас скоро буде пара у $groupName, не забудьте про це")
            }
            else -> {
                buildNotification(context,"Нагадування про пару", "Скоро буде пара")
            }
        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        Notify
            .with(context)
            .content {
                this.title = title
                text = message
            }
            .show()
    }

}