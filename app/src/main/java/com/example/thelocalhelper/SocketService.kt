package com.example.thelocalhelper

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class SocketService {
    lateinit var msocket: Socket
    init {
        try {
            msocket = IO.socket("http://172.60.104.58:3000")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        msocket.connect()
    }
    fun getSingletonConnection():Socket {
        try {
            if (msocket == null) {

                msocket = IO.socket("http://172.60.104.58:3000")
                msocket.connect()
                return msocket
            } else
                return msocket
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return msocket
    }
}