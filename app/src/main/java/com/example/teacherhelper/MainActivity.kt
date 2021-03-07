package com.example.teacherhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.teacherhelper.alert.AlertFragment
import com.example.teacherhelper.databinding.ActivityMainBinding
import com.example.teacherhelper.groups.GroupsFragment
import com.example.teacherhelper.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationView()
        loadFragment(GroupsFragment())
    }

    private fun setupBottomNavigationView() {
            binding.appBar.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_item_schedule->{
                        loadFragment(ScheduleFragment())
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_item_groups->{
                        loadFragment(GroupsFragment())
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_item_alert->{
                        loadFragment(AlertFragment())
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}