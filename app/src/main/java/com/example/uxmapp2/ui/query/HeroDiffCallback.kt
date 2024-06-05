package com.example.uxmapp2.ui.query

import androidx.recyclerview.widget.DiffUtil
import com.example.uxmapp2.data.characterData.Result

class HeroDiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}
