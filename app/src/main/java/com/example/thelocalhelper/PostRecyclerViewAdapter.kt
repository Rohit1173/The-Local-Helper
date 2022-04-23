package com.example.thelocalhelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostRecyclerViewAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>(){
    /*
    Reference : https://youtu.be/oDfl-xLXiac
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewtemp = LayoutInflater.from(parent.context).inflate(R.layout.post_recycler_view_item,parent,false)
        return ViewHolder(viewtemp)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val str = items[position]
        holder.item_text.text = str
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val item_text = view.findViewById<TextView>(R.id.post_title)
    }
}