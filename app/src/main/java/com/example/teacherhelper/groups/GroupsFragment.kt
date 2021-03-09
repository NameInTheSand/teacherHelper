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
        binding.groupsRecyclerView.layoutManager = LinearLayoutManager(context)
        setGroupsList()
    }

    private fun setGroupsList() {
        viewModel.groups.observe(viewLifecycleOwner, Observer {
            binding.groupsRecyclerView.adapter = GroupsAdapter(it) { selectedItem: Groups ->
                itemClicked(
                    selectedItem
                )
            }
        })
    }

    private fun attachListeners() {
        binding.addButton.setOnClickListener {
            openDialog()
        }
        binding.searchButton.setOnClickListener {
            hideKeyboardFrom(requireContext(), binding.searchButton)
            binding.searchInput.clearFocus()
            viewModel.viewModelScope.launch {
                viewModel.search(binding.searchInput.text.toString())
                delay(1000L)
                var search = viewModel.searchResult
                binding.groupsRecyclerView.adapter = GroupsAdapter(search) { selectedItem: Groups ->
                    itemClicked(
                        selectedItem
                    )
                }
                binding.searchInput.text = null
                binding.addButton.visibility = View.GONE
            }
        }
        binding.backButton.setOnClickListener {
            setupRecyclerView()
            binding.searchInput.text = null
            binding.addButton.visibility = View.VISIBLE
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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
            var name: String
            var course: Int
            if (!dialogView.findViewById<TextInputEditText>(R.id.group_enter_filed).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.course_enter_filed).text.toString()
                    .isNullOrEmpty()
            ) {
                name =
                    dialogView.findViewById<TextInputEditText>(R.id.group_enter_filed).text.toString()
                course =
                    dialogView.findViewById<TextInputEditText>(R.id.course_enter_filed).text.toString()
                        .toInt()
                viewModel.insert(Groups(name = name, course = course))
            } else {
                Toast.makeText(context, "Будь ласка, введіть данні", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
    }

    private fun itemClicked(groups: Groups) {
       findNavController(R.id.groups_fragment)?.navigate(
           GroupsFragmentDirections.groupInfo(groups.name,groups.course)
       )
    }
}
