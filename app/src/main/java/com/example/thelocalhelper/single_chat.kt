package com.example.thelocalhelper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class single_chat : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_single_chat, container, false)
        val re:RecyclerView=v.findViewById(R.id.recycler)
        var nearby_people: MutableList<String> = mutableListOf(
            "Rohit","Ayush","Yash","Rishi"
        )
         re.adapter=single_chat_adapter(nearby_people)
        re.setHasFixedSize(true)

        return v;
    }

}