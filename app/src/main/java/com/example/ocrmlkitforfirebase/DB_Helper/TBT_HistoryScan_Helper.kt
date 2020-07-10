package com.example.ocrmlkitforfirebase.DB_Helper

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.ConnectionClass
import com.example.ocrmlkitforfirebase.DB_MSSQL.TBM_Location
import com.example.ocrmlkitforfirebase.DB_MSSQL.TBT_HistoryScan
import com.example.ocrmlkitforfirebase.QRCode.HistoryScanAdapter
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception

class TBT_HistoryScan_Helper {

    lateinit var ctx: Context
    lateinit var connectionClass: ConnectionClass
    private var isConnected = false
    private lateinit var rv: RecyclerView
    private lateinit var query: String
    private lateinit var adapter: RecyclerView.Adapter<*>
    private var recordCount: Int = 0
    private var functionType: String = ""
    private lateinit var records: ArrayList<Any>

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    inner class SyncData : AsyncTask<String, String, String>() {
        private var message = ""
        lateinit var prog: ProgressDialog

        override fun onPreExecute() {
            records.clear()
            recordCount = 0
            prog = ProgressDialog.show(ctx, "Reading Data. . . . ", " Loading.. Please Wait.", true)
        }


        override fun doInBackground(vararg params: String?): String {
            try {
                var myCon = connectionClass?.dbCon()
                if (myCon == null) {
                    isConnected = false
                } else {
                    val statement = myCon!!.createStatement()
                    val cursor = statement.executeQuery(query)
                    if (cursor != null) {
                        while (cursor!!.next()) {
                            try {
                                when (functionType) {
                                    "getHistoryScan" -> records?.add(
                                        TBT_HistoryScan(
                                            cursor!!.getString("AccountUsername"),
                                            cursor!!.getString("LocationPath"),
                                            cursor!!.getInt("TypeCheck"),
                                            cursor!!.getString("Date")
                                        )
                                    )

                                    "getLocationScan" -> records?.add(
                                        TBM_Location(
                                            cursor!!.getString("LocationPath"),
                                            cursor!!.getString("LocationName"),
                                            cursor!!.getInt("IsActive")
                                        )
                                    )
                                }
                                recordCount++
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                        message = "Found $recordCount"
                        isConnected = true
                    } else {
                        message = "No Records"
                        isConnected = false
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                val writer = StringWriter()
                ex.printStackTrace(PrintWriter(writer))
                message = writer.toString()
                isConnected = false
            }
            return message
        } // doInBackground

        override fun onPostExecute(result: String?) {
            prog.dismiss()

            if (isConnected == true) {
                try {

                    if (functionType == "getLocationScan") {
                        if (recordCount != 0) {

                        }
                    } else {
                        rv.adapter = adapter
                    }

                } catch (ex: Exception) {

                }
            }
        }
    } // inner class SyncData

    fun getHistoryScan(rv: RecyclerView) {
        this.rv = rv
        query = "SELECT * FROM TBT_HistoryScan ORDER BY Date DESC"
        records = ArrayList<TBT_HistoryScan>() as ArrayList<Any>
        adapter =
            HistoryScanAdapter(records as ArrayList<TBT_HistoryScan>)
        functionType = "getHistoryScan"
        SyncData().execute("")
    }

    fun getLocationScan(path: String) {
        Log.d("recordCount", "getLocationScan" + path)
        query = "SELECT * FROM TBM_Location WHERE LocationPath = '$path'"
        records = ArrayList<TBM_Location>() as ArrayList<Any>
        functionType = "getLocationScan"
        SyncData().execute("")
    }

    fun insertLocationScan(path: String, title : String) {
        query = "INSERT INTO TBT_HistoryScan " +
                "(AccountUsername" +
                ",LocationPath" +
                ",TypeCheck" +
                ",Date) " +
                "VALUES" +
                "('$title'" +
                ",'$path'" +
                " ,1" +
                ",GETDATE())"
        Log.d("query", query)

        records = ArrayList<TBM_Location>() as ArrayList<Any>
        functionType = ""
        SyncData().execute("")
    }
}
