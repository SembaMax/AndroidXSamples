package com.semba.androidsamples.Shared

import android.os.Environment
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception

class Logger {

    private var TAG = "ARCH_COMPONENTS"
    private val logFile: File?

    //Init on application class
    init
    {
        logFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + "ArchLogger.txt")
        if (!logFile.exists())
        {
            try {
                logFile.createNewFile()
            }
            catch (e: IOException)
            {
                this.exception("",e)
            }
        }
    }


    fun<T> withTag(ofClass: Class<T>): Logger
    {
        this.TAG = ofClass.name
        return this
    }

    fun withTag(tag: String): Logger
    {
        this.TAG = tag
        return this
    }

    fun e(msg: String)
    {
        Log.e(TAG,msg)
        bufferLog(msg,null)
    }

    fun exception(msg: String = "", e: Exception)
    {
        Log.e(TAG,msg,e)
        bufferLog(msg,e)
    }

    fun bufferLog(msg: String = "", e: Exception?)
    {
        try {
            val buffer = BufferedWriter(FileWriter(logFile, true))
            buffer.append("$TAG : $msg  : $e")
            buffer.newLine()
            buffer.close()
        }
        catch (e: IOException)
        {
            this.exception("",e)
        }
    }
}