package com.example.ocrmlkitforfirebase.Ocr

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.TBM_ImagesOcr
import com.example.ocrmlkitforfirebase.R

class HistoryOcrAdapter (val historyOcrs: ArrayList<TBM_ImagesOcr>) : RecyclerView.Adapter<HistoryOcrAdapter.ViewHolder> ()
{
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtTitle = itemView.findViewById (R.id.txtTitle) as TextView
        val txtText = itemView.findViewById (R.id.txtText) as TextView
        val layout = itemView.findViewById(R.id.recyview_layout) as ConstraintLayout
    }

    override fun onCreateViewHolder (parent : ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recy_list_ocr, parent, false)
        return ViewHolder(
            v
        )
    }

    override fun getItemCount () : Int {
        return historyOcrs.size
    }

    override fun onBindViewHolder (holder : ViewHolder, position: Int){
        val historyOcr : TBM_ImagesOcr = historyOcrs [position]
        holder?.txtTitle.text = historyOcr.Title
        holder?.txtText.text = historyOcr.Text
        holder.layout.setOnClickListener {
            Log.d("onBindViewHolder", "onBindViewHolder: ")
        }
    }
}