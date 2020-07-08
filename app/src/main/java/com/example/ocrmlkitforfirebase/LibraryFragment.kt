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
import com.example.ocrmlkitforfirebase.Ocr.GalleryActivity
import com.example.ocrmlkitforfirebase.Ocr.ImageProcess
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val btn_add = view.findViewById<FloatingActionButton>(R.id.fab_add) as FloatingActionButton
        val btn_camera= view.findViewById<FloatingActionButton>(R.id.fab_camera) as FloatingActionButton
        val btn_gallery = view.findViewById<FloatingActionButton>(R.id.fab_gallery) as FloatingActionButton

        val fabOpen = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.fab_close)
        val fabClockwise = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.rotate_clockwise)
        val fabAntiClockwise = AnimationUtils.loadAnimation(activity!!.baseContext,R.anim.rotate_anticlockwise)

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
//            activity?.let {
//                val intent = Intent(it, ImageProcess::class.java)
//                intent.putExtra("typeProcess","camera")
//                it.startActivity(intent)
//            }
            activity?.let {
                val intent = Intent(it, GalleryActivity::class.java)
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

    fun newInstance(username:String): LibraryFragment {
        val fragment = LibraryFragment()
        val bundle = Bundle()
        bundle.putString("username", username)
        fragment.setArguments(bundle)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
//            account_username = bundle.getString("username").toString()
        }
    }


}