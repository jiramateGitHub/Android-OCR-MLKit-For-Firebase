package com.example.ocrmlkitforfirebase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_library, container, false)

        var btn_add = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.floatingActionButton) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn_add.setOnClickListener{
            activity?.let{
                val intent = Intent (it, Ocr::class.java)
                it.startActivity(intent)
            }
        }

        return view
    }


}