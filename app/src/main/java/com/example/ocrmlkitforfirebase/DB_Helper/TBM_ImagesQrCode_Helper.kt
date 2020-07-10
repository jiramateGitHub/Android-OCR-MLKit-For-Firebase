package com.example.ocrmlkitforfirebase.DB_Helper

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.ConnectionClass
import com.example.ocrmlkitforfirebase.DB_MSSQL.TBM_ImagesQrCode
import com.example.ocrmlkitforfirebase.QRCode.HistoryScanAdapter
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception

class TBM_ImagesQrCode_Helper {

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
        } // onPreExecute

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
                                    "getImagesQrCode" -> records?.add(
                                        TBM_ImagesQrCode(
                                            cursor!!.getString("AccountUsername"),
                                            cursor!!.getString("LocationPath"),
                                            cursor!!.getString("Date"),
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
                    rv.adapter = adapter
                } catch (ex: Exception) {

                }
            }
        } // onPostExecute
    } // inner class SyncData

    fun getImagesQrCode(rv: RecyclerView) {
        this.rv = rv
        query = "SELECT * FROM TBM_ImagesQrCode ORDER BY Date DESC"
        Log.d("LogQuery", query)
        records = ArrayList<TBM_ImagesQrCode>() as ArrayList<Any>
        adapter = HistoryScanAdapter(records as ArrayList<TBM_ImagesQrCode>)
        functionType = "getImagesQrCode"
        SyncData().execute("")
    }

    fun insertImagesQrCode(path: String, title : String) {
        query = "INSERT INTO TBM_ImagesQrCode (AccountUsername, LocationPath, TypeCheck, Date) " +
                "VALUES ('$title', '$path', 1, GETDATE())"
        Log.d("LogQuery", query)
        records = ArrayList<TBM_ImagesQrCode>() as ArrayList<Any>
        functionType = ""
        SyncData().execute("")
    }
}
