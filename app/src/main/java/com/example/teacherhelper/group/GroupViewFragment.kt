package com.example.teacherhelper.group

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherhelper.Navigable
import com.example.teacherhelper.database.AppDatabase
import com.example.teacherhelper.database.GroupsDAO
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.databinding.FragmentGroupViewBinding
import com.example.teacherhelper.factories.ViewModelFactory
import com.example.teacherhelper.groups.GroupsViewModel

class GroupViewFragment : Fragment() {
    private lateinit var binding: FragmentGroupViewBinding
    private lateinit var viewModel: GroupViewFragmentVM

    private val institutionAdapter = GroupViewAdapter().also { adapter ->
        adapter.setOnItemClickListener { _, group ->

        }
    }

    private val args by navArgs<GroupViewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupViewBinding.inflate(inflater)
        return binding.root
    }

    private var navigable: Navigable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigable = context as? Navigable
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao: GroupsDAO = AppDatabase.getInstance(activity?.application!!).groupsDAO
        val repository = GruopsRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GroupViewFragmentVM::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getStudents(args.groupId)
        setupRecyclerView()
        observeViewModel(viewModel)
    }


    private fun setupRecyclerView() {
        binding.studentRv.layoutManager = LinearLayoutManager(context)
        binding.studentRv.adapter = institutionAdapter
    }

    private fun observeViewModel(viewModel: GroupViewFragmentVM) {
        viewModel.studentList.observe(viewLifecycleOwner, {  delegateModel->
            delegateModel.mark = "10/10"
             institutionAdapter.notifyData(mutableListOf(delegateModel))
        })
    }

}