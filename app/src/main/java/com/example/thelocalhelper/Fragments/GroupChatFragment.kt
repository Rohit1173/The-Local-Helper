package com.example.thelocalhelper.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.thelocalhelper.Adapters.GroupChatAdapter
import com.example.thelocalhelper.Data.Message
import com.example.thelocalhelper.Data.Recieve
import com.example.thelocalhelper.Data.SendData
import com.example.thelocalhelper.Data.UserData
import com.example.thelocalhelper.R
import com.example.thelocalhelper.SocketService
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import io.socket.client.Socket


class GroupChatFragment : Fragment() {
    val gson: Gson = Gson()
    lateinit var re: RecyclerView
    lateinit var send: FloatingActionButton
    lateinit var msgtext: EditText
    lateinit var msocket: Socket
    lateinit var data: String
    var list = mutableListOf<Message>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_group_chat, container, false)
        val username = requireActivity().getIntent().getExtras()!!.getString("username").toString()
        msocket = SocketService().getSingletonConnection()
//        msocket.connect()
//        msocket.on(Socket.EVENT_CONNECT){
//            Toast.makeText(requireContext(),it[0].toString(),Toast.LENGTH_LONG).show()
//        }
        msocket.on(Socket.EVENT_CONNECT) {
            val userdata = UserData(username, "rohit")
            val jsondata2 = gson.toJson(userdata)
            msocket.emit("user", jsondata2)

        }
        msocket.on("connection") {
            requireActivity().runOnUiThread {
                data = it[0].toString()
                add_message(Message("ME", data, 3))
            }
        }
        msocket.on("chat message") {
            requireActivity().runOnUiThread {
                val mychat: Recieve = gson.fromJson(it[0].toString(), Recieve::class.java)
                add_message(Message(mychat.username, mychat.chat, 1))
            }
        }
        re = v.findViewById(R.id.grp_chat_recycler)
        send = v.findViewById(R.id.send_btn)
        msgtext = v.findViewById(R.id.msgtext)

        send.setOnClickListener {
            val msg: String = msgtext.text.toString().trim()
            if (msg.isNotEmpty()) {
                val senddata = SendData(msg)
                val jsondata = gson.toJson(senddata)
                msocket.emit("chat message", jsondata)
                add_message(Message(username, msg, 2))
                msgtext.text.clear()
//                msgtext.hidekeyboard()
            }
        }
        return v
    }

    private fun add_message(message: Message) {
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
