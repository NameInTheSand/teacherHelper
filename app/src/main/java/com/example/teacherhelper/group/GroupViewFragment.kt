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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.teacherhelper.Navigable
import com.example.teacherhelper.R
import com.example.teacherhelper.database.*
import com.example.teacherhelper.databinding.FragmentGroupViewBinding
import com.example.teacherhelper.factories.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GroupViewFragment : Fragment() {
    private lateinit var binding: FragmentGroupViewBinding
    private lateinit var viewModel: GroupViewFragmentVM
    var list: MutableList<StudentModel> = mutableListOf()
    var listThems: List<Thems> = mutableListOf()
    var filteredThemsList: List<Thems> = mutableListOf()
    var theme: HashMap<StudentModel, List<Thems>> = HashMap()
    var studentAdded = false


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
        attachListeners()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel(viewModel)
    }

    private fun attachListeners() {
        binding.addButton.setOnClickListener {
            openDialogStudentAdd()
        }
        binding.addTheme.setOnClickListener {
            openDialogThemeAdd()
        }
        binding.refresh.setOnRefreshListener {
            list.clear()
            theme.clear()
            binding.refresh.isRefreshing=true
            refresh()
            binding.refresh.isRefreshing=false
        }
    }
    private fun refresh(){
        viewModel.students.value.let {
            if (!it.isNullOrEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    var filteredList = it.filter {
                        it.groupName == args.groupId
                    }
                    filteredList.forEach { student ->
                        var mark = 0
                        var maxMark = 0
                        var passed = false
                        delay(20L)
                        filteredThemsList = listThems.filter {
                            it.studentId == student.id.toString()
                        }
                        if (filteredThemsList.isNullOrEmpty()) {
                            val allGroupThems = listThems.filter {
                                it.groupName == args.groupId
                            }
                            if (!allGroupThems.isNullOrEmpty()) {
                                var allGroupThemsWithoutRepeat: MutableList<Thems> =
                                    mutableListOf()
                                allGroupThemsWithoutRepeat.add(allGroupThems[0])
                                var themeName: MutableList<String> = mutableListOf()
                                themeName.add(allGroupThems[0].name)
                                allGroupThems.forEach { theme ->
                                    if (!themeName.contains(theme.name)) {
                                        allGroupThemsWithoutRepeat.add(theme)
                                        themeName.add(theme.name)
                                    }
                                }
                                allGroupThemsWithoutRepeat.forEach {
                                    viewModel.insertTheme(
                                        Thems(
                                            mark = 0,
                                            maxMark = it.maxMark,
                                            name = it.name,
                                            groupName = it.groupName,
                                            studentId = student.id.toString()
                                        )
                                    )
                                }
                                allGroupThemsWithoutRepeat.clear()
                                themeName.clear()
                            }
                            filteredThemsList = listThems.filter {
                                it.studentId == student.id.toString()
                            }
                        }
                        filteredThemsList.forEach {
                            mark += it.mark
                            maxMark += it.maxMark
                        }
                        if (mark != 0 && maxMark / mark >= 2) passed = true
                        list.add(StudentModel(student, "$mark/$maxMark", passed))
                        filteredThemsList.forEach {
                            theme.put(
                                StudentModel(student, "$mark/$maxMark", passed),
                                filteredThemsList
                            )
                        }
                    }
                    binding.studentRv.setAdapter(GroupViewAdapter(list, theme))
                }

            }
        }
    }

    private fun observeViewModel(viewModel: GroupViewFragmentVM) {
        viewModel.thems.observe(viewLifecycleOwner, {
            listThems = it
        })
        viewModel.students.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch {
                    refresh()
            }
        })
    }


    private fun openDialogStudentAdd() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("?????????????????? ???????????? ????????????????")
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
                viewModel.insertStudent(
                    Student(
                        name = name,
                        surname = surname,
                        groupName = args.groupId
                    )
                )
                list.clear()
                theme.clear()
            } else {
                Toast.makeText(context, "???????? ??????????, ?????????????? ??????????", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
    }

    private fun openDialogThemeAdd() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_theme, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("?????????????????? ???????????? ????????????????")
        val alertDialog = builder.show()
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            var name: String
            var maxMark: Int
            if (!dialogView.findViewById<TextInputEditText>(R.id.name_theme_enter_field).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.max_mark_enter).text.toString()
                    .isNullOrEmpty()
            ) {
                name =
                    dialogView.findViewById<TextInputEditText>(R.id.name_theme_enter_field).text.toString()
                maxMark =
                    dialogView.findViewById<TextInputEditText>(R.id.max_mark_enter).text.toString()
                        .toInt()
                viewModel.students.value?.forEach {
                    if (it.groupName == args.groupId) {
                        viewModel.insertTheme(
                            Thems(
                                name = name,
                                mark = 0,
                                maxMark = maxMark,
                                groupName = args.groupId,
                                studentId = it.id.toString()
                            )
                        )
                    }
                }
            } else {
                Toast.makeText(context, "???????? ??????????, ?????????????? ??????????", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
    }

}