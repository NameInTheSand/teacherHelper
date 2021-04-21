package com.example.teacherhelper.group

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.BR
import com.example.teacherhelper.R
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.database.Student
import com.example.teacherhelper.databinding.ItemGroupBinding
import com.example.teacherhelper.utils.AbsAdapter
import com.example.teacherhelper.utils.inflate

class GroupViewAdapter : AbsAdapter<StudentModel, GroupViewAdapter.StudentViewHolder>() {

    private var onClickListener: OnClickListener<Student>? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupViewAdapter.StudentViewHolder {
        return StudentViewHolder(
            parent.context.inflate(
                R.layout.item_student,
                parent,
                false
            )
        )
    }

    fun onItemClicked(view: View, student: StudentModel) {
        onClickListener?.onClick(getItemPosition(student), view, student.student)
    }

    fun setOnItemClickListener(callback: (Int, Student) -> Unit) {
        onClickListener = object : OnClickListener<Student> {
            override fun onClick(position: Int, view: View, item: Student) {
                callback(position, item)
            }
        }
    }

    override fun onBindViewHolder(holder: GroupViewAdapter.StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemGroupBinding? = DataBindingUtil.bind(itemView)
            private set

        fun bind(model: StudentModel?) {
            if (model != null) {
                binding?.setVariable(BR.model, model)
                binding?.setVariable(BR.adapter, this@GroupViewAdapter)
                binding?.executePendingBindings()
            }
        }
    }

}