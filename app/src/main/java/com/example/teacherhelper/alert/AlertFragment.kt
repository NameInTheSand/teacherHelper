package com.example.teacherhelper.alert

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.teacherhelper.databinding.FragmentAlertBinding
import com.example.teacherhelper.service.AlarmService
import java.util.*


class AlertFragment : Fragment() {

    private lateinit var binding: FragmentAlertBinding
    lateinit var alarmService: AlarmService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertBinding.inflate(inflater)
        attachListenets()
        return binding.root
    }

    private fun attachListenets() {
        binding.datePicker.setOnClickListener {
            if (!binding.nameInput.text.toString()
                    .isNullOrEmpty() && !binding.courseInput.text.toString().isNullOrEmpty()
            ) {
                val title = binding.nameInput.text.toString()
                val message = binding.courseInput.text.toString()
                alarmService = AlarmService(requireContext())
                setAlarm { alarmService.setExactAlarm(it,title,message) }
            } else Toast.makeText(requireContext(), "Введіть усі данні", Toast.LENGTH_SHORT).show()
        }
        binding.sendButton.setOnClickListener {
            Toast.makeText(requireContext(), "Нагадування було успішно додано", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        requireContext(),
                        0,
                        { _, hour, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}