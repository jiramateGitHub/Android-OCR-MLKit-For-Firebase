package com.example.ocrmlkitforfirebase.DB_MSSQL

import android.os.StrictMode
import android.util.Log
import com.example.ocrmlkitforfirebase.DB_Helper.TBT_HistoryScan_Helper
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionClass {
    private val ip = "192.168.228.146:1433"
    private val db = "DB_Vision"
    private val username = "sa"
    private val password = "123456"

    companion object{
        var thsh = TBT_HistoryScan_Helper()
    }

    fun dbCon() : Connection? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn : Connection? = null
        var connString : String? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connString = "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$username;password=$password;"
            conn = DriverManager.getConnection(connString)
        }catch (ex : SQLException){
            Log.d("Error", "SQLException: "+ex.message)
        }catch (ex : Exception){
            Log.d("Error", "Exception: "+ex.message)
        }
        return conn
    }
}