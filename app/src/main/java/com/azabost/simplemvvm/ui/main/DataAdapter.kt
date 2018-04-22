package com.azabost.simplemvvm.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azabost.simplemvvm.R
import com.azabost.simplemvvm.persistence.dao.CommitWithAuthor
import kotlinx.android.synthetic.main.commit_item.view.*

class DataAdapter : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private val items = mutableListOf<CommitWithAuthor>()

    fun setItems(newItems: List<CommitWithAuthor>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.commit_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.view.author.text = item.login
        holder.view.sha.text = item.sha
        holder.view.message.text = item.message
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}