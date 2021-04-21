package com.example.teacherhelper.group

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teacherhelper.Navigable
import com.example.teacherhelper.R
import com.example.teacherhelper.database.*
import com.example.teacherhelper.databinding.FragmentGroupViewBinding
import com.example.teacherhelper.factories.ViewModelFactory
import com.example.teacherhelper.groups.GroupsViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

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
        attachListeners()
    }

    private fun attachListeners() {
        binding.addButton.setOnClickListener {
            openDialogStudentAdd()
        }
    }
    private fun setupRecyclerView() {
        binding.studentRv.layoutManager = LinearLayoutManager(context)
        binding.studentRv.adapter = institutionAdapter
    }

    private fun observeViewModel(viewModel: GroupViewFragmentVM) {
        viewModel.studentList.observe(viewLifecycleOwner, {  delegateModel->
            institutionAdapter.notifyData(delegateModel)
        })
    }

    private fun openDialogStudentAdd() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Додавання нового студента")
        val alertDialog = builder.show()
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            var name: String
            var surname: String
            if (!dialogView.findViewById<TextInputEditText>(R.id.name_enter_filed).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.surname_enter_filed).text.toString()
                    .isNullOrEmpty()
            ) {
                name =
                    dialogView.findViewById<TextInputEditText>(R.id.name_enter_filed).text.toString()
                surname =
                    dialogView.findViewById<TextInputEditText>(R.id.surname_enter_filed).text.toString()
                viewModel.insert(Student(name = name, surname = surname, groupName = args.groupId))
            } else {
                Toast.makeText(context, "Будь ласка, введіть данні", Toast.LENGTH_SHORT).show()
            }
            viewModel.getStudents(args.groupId)
            alertDialog.dismiss()
        }
    }

}