package com.example.teacherhelper.groups

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherhelper.BR
import com.example.teacherhelper.R
import com.example.teacherhelper.database.Groups
import com.example.teacherhelper.databinding.ItemGroupBinding
import com.example.teacherhelper.utils.AbsAdapter
import com.example.teacherhelper.utils.inflate

class GroupsAdapter : AbsAdapter<Groups, GroupsAdapter.groupViewHolder>() {

    private var onClickListener: OnClickListener<Groups>? = null
    private var onClickListenerMinus: OnClickListener<Groups>? = null
    private var onClickListenerPlus: OnClickListener<Groups>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): groupViewHolder {
        return groupViewHolder(
            parent.context.inflate(
                R.layout.item_group,
                parent,
                false
            )
        )
    }

    fun onItemClicked(view: View, group: Groups) {
        onClickListener?.onClick(getItemPosition(group), view, group)
    }

    fun onClickListenerMinus(view: View, group: Groups) {
        onClickListenerMinus?.onClick(getItemPosition(group), view, group)
    }

    fun onClickListenerPlus(view: View, group: Groups) {
        onClickListenerMinus?.onClick(getItemPosition(group), view, group)
    }

    fun setOnItemClickListener(callback: (Int, Groups) -> Unit) {
        onClickListener = object : OnClickListener<Groups> {
            override fun onClick(position: Int, view: View, item: Groups) {
                callback(position, item)
            }
        }
    }

    fun setOnClickListenerMinus(callback: (Int, Groups) -> Unit) {
        onClickListenerMinus = object : OnClickListener<Groups> {
            override fun onClick(position: Int, view: View, item: Groups) {
                callback(position, item)
            }
        }
    }

    fun setOnClickListenerPlus(callback: (Int, Groups) -> Unit) {
        onClickListenerPlus = object : OnClickListener<Groups> {
            override fun onClick(position: Int, view: View, item: Groups) {
                callback(position, item)
            }
        }
    }

    override fun onBindViewHolder(holder: groupViewHolder, position: Int) {
       holder.bind(getItem(position))
    }


    class groupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemGroupBinding? = DataBindingUtil.bind(itemView)
            private set

        fun bind(model: Groups?) {
            if (model != null) {
                binding?.setVariable(BR.model, model)
                binding?.setVariable(BR.adapter,GroupsAdapter)
                binding?.executePendingBindings()
            }
        }
    }


}
