package com.example.teacherhelper.groups

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherhelper.R
import com.example.teacherhelper.database.AppDatabase
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.database.GroupsDAO
import com.example.teacherhelper.database.GruopsRepository
import com.example.teacherhelper.databinding.FragmentGroupsBinding
import com.example.teacherhelper.factories.ViewModelFactory
import com.example.teacherhelper.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var viewModel: GroupsViewModel

    private val institutionAdapter = GroupsAdapter().also { adapter ->
            adapter.setOnItemClickListener { _, group ->
                findNavController().navigate(GroupsFragmentDirections.groupInfo(group.id.toString()))
            }
        adapter.setOnClickListenerMinus { _, groups ->
            viewModel.addHour(groups.hours-1,groups.id)
        }
        adapter.setOnClickListenerPlus { _, groups ->
            viewModel.addHour(groups.hours+1,groups.id)
        }
        adapter.setOnClickListenerEdit { _, groups ->
            openDialogEdit(groups)
        }
    }

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
        observeViewModel(viewModel)
        setupRecyclerView()
        attachListeners()
    }

    private fun setupRecyclerView() {
        binding.groupsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.groupsRecyclerView.adapter = institutionAdapter
    }

    private fun attachListeners() {
        binding.addButton.setOnClickListener {
            openDialog()
        }
        }

//    fun hideKeyboardFrom(context: Context, view: View) {
//        val imm: InputMethodManager =
//            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }

    private fun openDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_group, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("?????????????????? ?????????? ??????????")
        val alertDialog = builder.show()
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            if (!dialogView.findViewById<TextInputEditText>(R.id.group_enter_filed).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.course_enter_filed).text.toString()
                    .isNullOrEmpty()
            ) {
                val name =
                    dialogView.findViewById<TextInputEditText>(R.id.group_enter_filed).text.toString()
                val course =
                    dialogView.findViewById<TextInputEditText>(R.id.course_enter_filed).text.toString()
                        .toInt()
                viewModel.insert(Groups(name = name, course = course,hours = 0))
                Toast.makeText(context, "?????????? $name $course ???????? ????????????", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "???????? ??????????, ?????????????? ??????????", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
    }

    private fun openDialogEdit(groups: Groups) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_group, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("?????????????????????? ??????????")
        val alertDialog = builder.show()
        dialogView.findViewById<TextInputEditText>(R.id.group_edit_filed).setText(groups.name)
        dialogView.findViewById<TextInputEditText>(R.id.course_edit_filed).setText(groups.course.toString())
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            if (!dialogView.findViewById<TextInputEditText>(R.id.group_edit_filed).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.course_edit_filed).text.toString()
                    .isNullOrEmpty()
            ) {
                val name =
                    dialogView.findViewById<TextInputEditText>(R.id.group_edit_filed).text.toString()
                val course =
                    dialogView.findViewById<TextInputEditText>(R.id.course_edit_filed).text.toString()
                        .toInt()
                viewModel.updateGroup(name,course,groups.id)
                Toast.makeText(context, "?????????? ${groups.name} ${groups.course} ???????? ??????????????", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "???????? ??????????, ?????????????? ??????????", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.delete_button).setOnClickListener {
            viewModel.deleteGroup(groups.id.toString())
            Toast.makeText(context, "?????????? ${groups.name} ${groups.course} ???????? ????????????????", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }
    }

    private fun observeViewModel(viewModel: GroupsViewModel) {
        viewModel.groups.observe(viewLifecycleOwner, {  delegateModel->
            delegateModel.let { institutionAdapter.notifyData(it) }
        })
    }
}
