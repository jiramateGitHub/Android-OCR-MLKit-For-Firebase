package com.example.ocrmlkitforfirebase.QRCode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.TBT_HistoryScan
import com.example.ocrmlkitforfirebase.R

class HistoryScanAdapter (val historyScans: ArrayList<TBT_HistoryScan>) : RecyclerView.Adapter<HistoryScanAdapter.ViewHolder> ()
{
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtLocationPath = itemView.findViewById (R.id.txtLocationPath) as TextView
        val txtDate = itemView.findViewById (R.id.txtDate) as TextView
    }

    override fun onCreateViewHolder (p0 : ViewGroup, p1: Int) : ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.recy_list_checkin, p0, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount () : Int {
        return historyScans.size
    }

    override fun onBindViewHolder (p0 : ViewHolder, p1: Int){
        val historyScan : TBT_HistoryScan = historyScans [p1]
        p0?.txtLocationPath.text = historyScan.LocationPath.toString ()
        p0?.txtDate.text = historyScan.Date
    }
}