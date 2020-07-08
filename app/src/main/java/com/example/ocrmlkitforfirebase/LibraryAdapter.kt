package com.example.ocrmlkitforfirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_list_image, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.Holder()
        holder.tv_title.setText(dataSource.getJSONObject(position).getString("position_work_name").toString() )
        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.iv_image)

        holder.layout.setOnClickListener {
            var key = dataSource.getJSONObject(position).getString("key").toString()
            var tv_title  = dataSource.getJSONObject(position).getString("tv_title").toString()
            var iv_image = dataSource.getJSONObject(position).getString("iv_image").toString()

//            val fm = thisActivity.supportFragmentManager
//            val transaction: FragmentTransaction = fm!!.beginTransaction()
//            val load_fragment = WorkExperienceInputFragment().newInstance(key, username, position_work_name, position_manage_name, position_level, manage_name, place, start_date, end_date, text_th) as WorkExperienceInputFragment
//            transaction.replace(R.id.contentContainer, load_fragment,"WorkExperienceInputFragment")
//            transaction.addToBackStack("WorkExperienceInputFragment")
//            transaction.commit()

        }

    }

}
