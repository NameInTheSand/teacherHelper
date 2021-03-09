package com.example.teacherhelper.alert

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.teacherhelper.databinding.FragmentAlertBinding
import com.example.teacherhelper.firebase.FirebaseService
import com.example.teacherhelper.notifications.NotificationData
import com.example.teacherhelper.notifications.PushNotification
import com.example.teacherhelper.notifications.RetrofitInstance
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

const val TOPIC = "topics/teacher"

class AlertFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    val TAG = "MainActivity"

    private lateinit var binding: FragmentAlertBinding
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

    var savedYear = 0
    var savedHour = 0
    var savedMonth = 0
    var savedDay = 0
    var savedMinute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertBinding.inflate(inflater)
        FirebaseService.sharedPref =
            this.activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            binding.testDate.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        attachListenets()
        return binding.root
    }


    private fun getDateTimeCalendar() {
        val calendar: Calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun attachListenets() {
        binding.datePicker.setOnClickListener {
            getDateTimeCalendar()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DatePickerDialog(requireContext(), this, year, month, day).show()
            }
        }
        binding.sendButton.setOnClickListener {
            if (!binding.nameInput.text.toString()
                    .isNullOrEmpty() && !binding.courseInput.text.toString().isNullOrEmpty()
            ) {
                val title = binding.nameInput.text.toString()
                val message = binding.courseInput.text.toString()
                val token = binding.testDate.text.toString()
                PushNotification(
                    NotificationData(title, message),
                    token
                ).also {
                    sendNotification(it)
                }
            } else Toast.makeText(requireContext(), "Введіть усі данні", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        savedDay = day
        savedMonth = month
        savedYear = year
        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        savedHour = hour
        savedMinute = minute
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "Alert: ${response.body().toString()}")
                } else {
                    Log.e("Alert", response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e("Alert", e.toString())
            }
        }
}