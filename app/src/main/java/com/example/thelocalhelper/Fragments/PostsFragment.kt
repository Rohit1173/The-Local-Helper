package com.example.thelocalhelper.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thelocalhelper.Adapters.PostRecyclerViewAdapter
import com.example.thelocalhelper.R


class PostsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_posts, container, false)

        val arrstr = ArrayList<String>()
        arrstr.add("Need help finding my lost dog")
        arrstr.add("Suggestions for good place to visit in Lucknow?")
        arrstr.add("Looking for a second hand car in lucknow")

        val recyclerView = v.findViewById<RecyclerView>(R.id.post_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = PostRecyclerViewAdapter(arrstr)
        return v
    }
}
