package com.example.thelocalhelper

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class GroupChatFragment : Fragment() {
    lateinit var re: RecyclerView
    lateinit var send: FloatingActionButton
    lateinit var msgtext: EditText
    var list = mutableListOf<message>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_group_chat, container, false)
        re = v.findViewById(R.id.grp_chat_recycler)
        send = v.findViewById(R.id.send_btn)
        msgtext = v.findViewById(R.id.msgtext)
        val username = requireActivity().getIntent().getExtras()!!.getString("username").toString()
        send.setOnClickListener {
            val msg: String = msgtext.text.toString().trim()
            if (msg.isNotEmpty()) {
                add_message(message(username, "kwdwb"))
                msgtext.text.clear()
                msgtext.hidekeyboard()
            }
        }
        return v
    }

    private fun add_message(message: message) {
        list.add(message)
        re.adapter = GroupChatAdapter(list)
        re.scrollToPosition(list.size - 1)
    }

    fun View.hidekeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


}