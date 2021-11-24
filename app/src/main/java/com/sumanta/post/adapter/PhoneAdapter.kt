package com.sumanta.post.adapter

import com.sumanta.post.databinding.EachRowBinding
import com.sumanta.post.model.Phone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sumanta.post.util.Listener
import javax.inject.Inject

class PhoneAdapter
constructor(private val listener: Listener) : ListAdapter<Phone, PhoneAdapter.PhoneViewHolder>(Diff) {


    inner class PhoneViewHolder(private val binding: EachRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(phone: Phone) {
            binding.apply {
                name.text = phone.name
                phoneNo.text = phone.phoneNo.toString()
                delete.setOnClickListener {
                    listener.onClickDelete(adapterPosition, phone.userId)
                }
            }
        }
    }

    object Diff : DiffUtil.ItemCallback<Phone>() {
        override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        return PhoneViewHolder(
            EachRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val phone = getItem(position)
        if (phone != null) {
            holder.bind(phone)
        }
    }
}