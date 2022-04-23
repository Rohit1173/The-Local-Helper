package com.example.thelocalhelper

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.thelocalhelper.Api.retrofitinstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class GroupChatFragment : Fragment() {
    lateinit var re: RecyclerView
    lateinit var send: FloatingActionButton
    lateinit var msgtext: EditText
    lateinit var msocket: Socket
    lateinit var data:String
    var list = mutableListOf<message>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_group_chat, container, false)
        try {
            msocket = IO.socket("http://172.60.104.58:3000")
        } catch (e: URISyntaxException) {

        }
        msocket.connect()
        msocket.on("channel") {
            requireActivity().runOnUiThread {
                try {
                    data = it[0].toString()
                }
                catch (e:Exception){
                    Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_LONG).show()
                }
                Toast.makeText(requireContext(),"hjdsvjdb",Toast.LENGTH_LONG).show()
            }
        }
        re = v.findViewById(R.id.grp_chat_recycler)
        send = v.findViewById(R.id.send_btn)
        msgtext = v.findViewById(R.id.msgtext)
        val username = requireActivity().getIntent().getExtras()!!.getString("username").toString()
        send.setOnClickListener {
            val msg: String = msgtext.text.toString().trim()
            if (msg.isNotEmpty()) {
                add_message(message(username, msg,2))
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