package com.example.teacherhelper.groups

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.teacherhelper.R
import com.example.teacherhelper.database.AppDatabase
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.database.GroupsDAO
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.databinding.FragmentGroupsBinding
import com.example.teacherhelper.factories.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var viewModel: GroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao: GroupsDAO = AppDatabase.getInstance(activity?.application!!).groupsDAO
        val repository = GruopsRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GroupsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupRecyclerView()
        attachListeners()
    }

    private fun setupRecyclerView() {
        viewModel.groups.observe(viewLifecycleOwner, Observer {
            Log.d("OBSERVE", it.toString())
        })
    }

    private fun attachListeners() {
        binding.floatingActionButton.setOnClickListener {
            openDialog()
        }
        binding.searchButton.setOnClickListener {
            viewModel.search(binding.searchInput.text.toString())
        }
    }

    private fun openDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_group, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Додавання нової групи")
        val alertDialog = builder.show()
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            viewModel.insert(Groups(name = dialogView.findViewById<TextInputEditText>(R.id.group_enter_filed).text.toString()
                ,course =dialogView.findViewById<TextInputEditText>(R.id.course_enter_filed).text.toString().toInt())
            )
            alertDialog.dismiss()
        }
    }
}