package com.example.thelocalhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupChatAdapter(private var list: MutableList<message>) :
    RecyclerView.Adapter<GroupChatAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chat_text: TextView =view.findViewById(R.id.sendmsg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.outgoing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chat_text.text = list[position].chat
    }

    override fun getItemCount(): Int {
        return list.size
    }
}