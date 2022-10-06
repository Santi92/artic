package com.example.artsproject

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun String.dateFormatShort() : String{
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date : Date = parser.parse(this)!!
        formatter.format(date)

    }catch (e: Exception){
        e.printStackTrace()
         this
    }
}