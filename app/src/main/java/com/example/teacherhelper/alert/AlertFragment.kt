package com.example.teacherhelper.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teacherhelper.databinding.FragmentAlertBinding

class AlertFragment : Fragment() {
    private lateinit var binding: FragmentAlertBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAlertBinding.inflate(inflater)
        return binding.root
    }
}