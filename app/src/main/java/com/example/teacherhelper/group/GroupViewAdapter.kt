package com.example.teacherhelper.group

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.teacherhelper.R
import com.example.teacherhelper.database.Thems
import com.example.teacherhelper.databinding.ItemStudentBinding
import com.example.teacherhelper.databinding.ItemThemeBinding
import com.example.teacherhelper.utils.inflate

class GroupViewAdapter(
    private var students: List<StudentModel>,
    private var theme: HashMap<StudentModel, List<Thems>>
) : BaseExpandableListAdapter() {


    override fun getGroupCount(): Int {
        return students.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return theme[students[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return students[p0]
    }

    override fun getChild(p0: Int, p1: Int): Thems? {
        return theme[students[p0]]?.get(p1)
    }

    override fun getGroupId(p0: Int): Long {
        return 0
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val view = p3?.context?.inflate(
            R.layout.item_student,
            p3,
            false
        )!!
        val binding: ItemStudentBinding = DataBindingUtil.bind(view)!!
        binding.model = students[p0]
        binding.executePendingBindings()
        return view
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val view = p4?.context?.inflate(
            R.layout.item_theme,
            p4,
            false
        )!!
        val binding: ItemThemeBinding = DataBindingUtil.bind(view)!!
        binding.model = getChild(p0, p1)
        binding.executePendingBindings()
        binding.itemGroup.setOnClickListener {
            Toast.makeText(p4.context, getChild(p0, p1)?.name, Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }


}