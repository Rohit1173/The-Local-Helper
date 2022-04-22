package com.example.thelocalhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class single_chat_adapter(private var list: MutableList<String>) :
    RecyclerView.Adapter<single_chat_adapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val re_text: TextView = view.findViewById(R.id.recycler_text)
        val context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.re_text.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}