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
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class GroupChatFragment : Fragment() {
    val gson: Gson = Gson()
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
        val username = requireActivity().getIntent().getExtras()!!.getString("username").toString()
        msocket= SocketService().getSingletonConnection()
//        msocket.connect()
//        msocket.on(Socket.EVENT_CONNECT){
//            Toast.makeText(requireContext(),it[0].toString(),Toast.LENGTH_LONG).show()
//        }


        msocket.on(Socket.EVENT_CONNECT) {
            val userdata = userdata(username, "rohit")
            val jsondata2 = gson.toJson(userdata)
            msocket.emit("user", jsondata2)

        }
        msocket.on("connection") {
            requireActivity().runOnUiThread {
                data = it[0].toString()
                add_message(message("ME", data, 3))
            }
        }
        msocket.on("chat message") {
            requireActivity().runOnUiThread {
                val mychat: recieve = gson.fromJson(it[0].toString(), recieve::class.java)
                add_message(message(mychat.username, mychat.chat, 1))
            }
        }
        re = v.findViewById(R.id.grp_chat_recycler)
        send = v.findViewById(R.id.send_btn)
        msgtext = v.findViewById(R.id.msgtext)

        send.setOnClickListener {
            val msg: String = msgtext.text.toString().trim()
            if (msg.isNotEmpty()) {
                val senddata = senddata(msg)
                val jsondata = gson.toJson(senddata)
                msocket.emit("chat message", jsondata)
                add_message(message(username, msg,2))
                msgtext.text.clear()
//                msgtext.hidekeyboard()
            }
        }
        return v
    }

    private fun add_message(message: message) {
        list.add(message)
        re.adapter = GroupChatAdapter(list)
        re.scrollToPosition(list.size - 1)
    }

//    fun View.hidekeyboard() {
//        val inputManager =
//            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.hideSoftInputFromWindow(windowToken, 0)
//    }


}