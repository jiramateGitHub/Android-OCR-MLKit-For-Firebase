package com.example.ocrmlkitforfirebase.Ocr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.TBM_ImagesOcr
import com.example.ocrmlkitforfirebase.R

class HistoryOcrAdapter (val historyOcrs: ArrayList<TBM_ImagesOcr>) : RecyclerView.Adapter<HistoryOcrAdapter.ViewHolder> ()
{
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtTitle = itemView.findViewById (R.id.txtTitle) as TextView
        val txtText = itemView.findViewById (R.id.txtText) as TextView
    }

    override fun onCreateViewHolder (p0 : ViewGroup, p1: Int) : ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.recy_list_ocr, p0, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount () : Int {
        return historyOcrs.size
    }

    override fun onBindViewHolder (p0 : ViewHolder, p1: Int){
        val historyOcr : TBM_ImagesOcr = historyOcrs [p1]
        p0?.txtTitle.text = historyOcr.Title
        p0?.txtText.text = historyOcr.Text
    }
}