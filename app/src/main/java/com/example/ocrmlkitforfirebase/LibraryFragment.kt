package com.example.ocrmlkitforfirebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.Ocr.ImageProcess
import com.example.ocrmlkitforfirebase.QRCode.CameraScan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_library.*
import org.json.JSONArray
import org.json.JSONObject

class LibraryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    var isOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_library, container, false)
        val btn_add = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_add) as com.google.android.material.floatingactionbutton.FloatingActionButton
        val btn_camera= view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_camera) as com.google.android.material.floatingactionbutton.FloatingActionButton
        val btn_gallery = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_gallery) as com.google.android.material.floatingactionbutton.FloatingActionButton
        val btn_qrcode = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_scan_qrcode) as com.google.android.material.floatingactionbutton.FloatingActionButton

        var action = (activity as MainActivity).supportActionBar
        action!!.title = "Library Images"

        //CONNECT FIREBASE
        val mRootRef = FirebaseDatabase.getInstance().reference
        val mMessagesRef = mRootRef.child("Images")

        mMessagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val list = JSONArray()
                recyclerView = view.findViewById(R.id.recyLayout)

                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
                recyclerView.layoutManager = layoutManager

                for (ds in dataSnapshot.children) {

                    val jObject = JSONObject()

                    val imagePath = ds.child("imagePath").getValue(String::class.java)!!
                    val imageText = ds.child("imageText").getValue(String::class.java)!!
                    val imageTitle = ds.child("imageTitle").getValue(String::class.java)!!

                    jObject.put("key", ds.key)
                    jObject.put("imagePath", imagePath)
                    jObject.put("imageText", imageText)
                    jObject.put("imageTitle", imageTitle)

                    list.put(jObject)
                }

                val adapter = LibraryAdapter(activity!!, list)

                recyclerView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        // ANIMATION FAB BUTTON
        val fabOpen = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.fab_close)
        val fabClockwise = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.rotate_clockwise)
        val fabAntiClockwise = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.rotate_anticlockwise)

        btn_add.setOnClickListener{
            if(isOpen){
                fab_camera.startAnimation(fabClose)
                fab_gallery.startAnimation(fabClose)
                btn_qrcode.startAnimation(fabClose)
                btn_add.startAnimation(fabClockwise)
                isOpen = false
            }else{
                fab_camera.startAnimation(fabOpen)
                fab_gallery.startAnimation(fabOpen)
                btn_qrcode.startAnimation(fabOpen)
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

        btn_qrcode.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CameraScan::class.java)
                it.startActivity(intent)
            }
        }

        return view
    }


}