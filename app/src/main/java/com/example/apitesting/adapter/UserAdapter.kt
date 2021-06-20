package com.example.apitesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apitesting.data.model.User
import com.example.apitesting.databinding.LayoutUsersListBinding

class UserAdapter : PagingDataAdapter<User, UserAdapter.ViewHolder>
    (

    object : DiffUtil.ItemCallback<User>()
    {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean
        {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean
        {
            return oldItem.toString() == newItem.toString()
        }
    }
)
{




    inner class  ViewHolder(view: LayoutUsersListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutUsersListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)

    } // onCreateViewHolder closed

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val user = getItem(position)
        LayoutUsersListBinding.bind(holder.itemView).apply()
        {
            textViewLayoutUsersUserName.text = user?.username
        } // binding closed

    } // onBindViewHolder closed




} // UserAdapter closed