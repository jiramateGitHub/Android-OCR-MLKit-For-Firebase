package com.example.ocrmlkitforfirebase.Ocr

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.ConnectionClass
import com.example.ocrmlkitforfirebase.MainActivity
import com.example.ocrmlkitforfirebase.R
import kotlinx.android.synthetic.main.fragment_library.*

class HistoryOcrFragment : Fragment() {

    lateinit var rv : RecyclerView
    var isOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_history_ocr, container, false)

        (activity as MainActivity).supportActionBar!!.title = "Scan Images Ocr"

        rv = view.findViewById(R.id.recyLayout)
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        ConnectionClass.db_img_ocr.connectionClass = ConnectionClass()
        ConnectionClass.db_img_ocr.ctx = requireContext()
        ConnectionClass.db_img_ocr.getImagesOcr(rv)

        val btn_add = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(
            R.id.fab_add
        ) as com.google.android.material.floatingactionbutton.FloatingActionButton
        val btn_camera= view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(
            R.id.fab_camera
        ) as com.google.android.material.floatingactionbutton.FloatingActionButton
        val btn_gallery = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(
            R.id.fab_gallery
        ) as com.google.android.material.floatingactionbutton.FloatingActionButton

        // ANIMATION FAB BUTTON
        val fabOpen = AnimationUtils.loadAnimation(activity!!.baseContext,
            R.anim.fab_open
        )
        val fabClose = AnimationUtils.loadAnimation(activity!!.baseContext,
            R.anim.fab_close
        )
        val fabClockwise = AnimationUtils.loadAnimation(activity!!.baseContext,
            R.anim.rotate_clockwise
        )
        val fabAntiClockwise = AnimationUtils.loadAnimation(activity!!.baseContext,
            R.anim.rotate_anticlockwise
        )

        btn_add.setOnClickListener{
            if(isOpen){
                fab_camera.startAnimation(fabClose)
                fab_gallery.startAnimation(fabClose)
                btn_add.startAnimation(fabClockwise)
                isOpen = false
            }else{
                fab_camera.startAnimation(fabOpen)
                fab_gallery.startAnimation(fabOpen)
                btn_add.startAnimation(fabAntiClockwise)
                isOpen = true
            }
        }

        btn_camera.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ImageProcess::class.java)
                intent.putExtra("typeProcess","camera")
                it.startActivity(intent)
            }
        }

        btn_gallery.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ImageProcess::class.java)
                intent.putExtra("typeProcess","gallery")
                it.startActivity(intent)
            }
        }

        return view
    }

}