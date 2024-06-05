package com.example.uxmapp2.source

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/"
        var PUBLIC_KEY: String = ""
        var PRIVATE_KEY: String = ""
        val timeStamp: String
            get() = Timestamp(System.currentTimeMillis()).time.toString()

        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}



