package com.example.ocrmlkitforfirebase.Ocr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ocrmlkitforfirebase.R
import org.json.JSONArray

class LibraryAdapter (fragmentActivity: FragmentActivity, val dataSource: JSONArray) : RecyclerView.Adapter<LibraryAdapter.Holder>() {

    private val thiscontext : Context = fragmentActivity.baseContext
    private val thisActivity = fragmentActivity

    class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val View = view
        lateinit var layout : LinearLayout
        lateinit var tv_title: TextView
        lateinit var iv_image: ImageView

        fun Holder(){
            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            tv_title = View.findViewById(R.id.tv_title) as TextView
            iv_image = View.findViewById(R.id.iv_image) as ImageView
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recy_list_image,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Holder()
        holder.tv_title.setText(dataSource.getJSONObject(position).getString("imageTitle").toString() )
        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("imagePath").toString())
            .into(holder.iv_image)

        holder.layout.setOnClickListener {
            var key = dataSource.getJSONObject(position).getString("key").toString()
            var imageTitle  = dataSource.getJSONObject(position).getString("imageTitle").toString()
            var imagePath = dataSource.getJSONObject(position).getString("imagePath").toString()
            var imageText = dataSource.getJSONObject(position).getString("imageText").toString()

            val fm = thisActivity.supportFragmentManager
            val transaction: FragmentTransaction = fm!!.beginTransaction()

            val load_fragment = ImageDetailFragment().newInstance(imagePath, imageTitle, imageText) as ImageDetailFragment
            transaction.replace(R.id.contentContainer, load_fragment,"ImageDetailFragment")
            transaction.addToBackStack("ImageDetailFragment")
            transaction.commit()
        }

    }

}
