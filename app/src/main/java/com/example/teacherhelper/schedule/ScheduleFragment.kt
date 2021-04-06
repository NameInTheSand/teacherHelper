package com.example.teacherhelper.schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teacherhelper.databinding.FragmentScheduleBinding


class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater)
        loadSite()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadSite(){
        val webView = binding.webView
        webView.loadUrl("http://asu.pnu.edu.ua/cgi-bin/timetable.cgi")
        val settings = webView.settings
        settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

    }

}