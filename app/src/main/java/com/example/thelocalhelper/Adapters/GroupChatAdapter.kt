package com.example.thelocalhelper.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thelocalhelper.R
import com.example.thelocalhelper.Data.Message

class GroupChatAdapter(private var list: MutableList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder1(itemview: View) : RecyclerView.ViewHolder(itemview) {  //incoming.xml
        val txt: TextView = itemview.findViewById(R.id.textview)
        val myname: TextView = itemview.findViewById(R.id.myname)
    }

    class ViewHolder2(itemview: View) : RecyclerView.ViewHolder(itemview) {  //outgoing.xml
        val send_msg: TextView = itemview.findViewById(R.id.sendmsg)
    }

    class ViewHolder3(itemview: View) : RecyclerView.ViewHolder(itemview) {    //join.xml
        val join_text: TextView = itemview.findViewById(R.id.join_text)
    }

    override fun getItemViewType(position: Int): Int {
        val items = list[position]
        return (items.viewType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        if (viewType == 1) {
            view = layoutInflater.inflate(R.layout.incoming, parent, false)
            return ViewHolder1(view)
        } else if (viewType == 2) {
            view = layoutInflater.inflate(R.layout.outgoing, parent, false)
            return ViewHolder2(view)

        } else {
            view = layoutInflater.inflate(R.layout.join, parent, false)
            return ViewHolder3(view)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if (getItemViewType(position) == 1) {
            (holder as ViewHolder1).txt.text = item.chat
            holder.myname.text = item.username
        } else if (getItemViewType(position) == 2) {
            (holder as ViewHolder2).send_msg.text = item.chat
        } else {
            (holder as ViewHolder3).join_text.text = item.chat
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
