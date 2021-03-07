package com.example.teacherhelper.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.teacherhelper.database.AppDatabase
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.databinding.FragmentGroupsBinding

class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var viewModel : GroupsViewModel

    private lateinit var allGroups:List<Groups>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentGroupsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupsViewModel::class.java)
        val database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "groupsDatabase")
            .allowMainThreadQueries()
            .build()
        allGroups= database.groupsDAO().getAllGroups()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        binding.cityRecyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter = GroupsAdapter(allGroups)
        }
    }
}