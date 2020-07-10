package com.example.ocrmlkitforfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ocrmlkitforfirebase.Ocr.HistoryOcrFragment
import com.example.ocrmlkitforfirebase.Ocr.LibraryFragment
import com.example.ocrmlkitforfirebase.QRCode.HistoryScanFragment

class MenuFragment : Fragment() {

    private lateinit var btnTxtRc: Button
    private lateinit var btnTxtRc2: Button
    private lateinit var btnSQRc: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_menu, container, false)

        (activity as MainActivity).supportActionBar!!.title = "Menu"

        val transaction = activity!!.supportFragmentManager.beginTransaction()

        btnTxtRc = view.findViewById(R.id.btnTxtRc)
        btnTxtRc2 = view.findViewById(R.id.btnTxtRc2)
        btnSQRc = view.findViewById(R.id.btnSQRc)

        btnTxtRc.setOnClickListener{
            transaction.replace(R.id.contentContainer, HistoryOcrFragment(), "HistoryOcrFragment")
            transaction.addToBackStack("HistoryOcrFragment").commit()
        }

        btnTxtRc2.setOnClickListener{
            transaction.replace(R.id.contentContainer, LibraryFragment(), "LibraryFragment")
            transaction.addToBackStack("LibraryFragment").commit()
        }

        btnSQRc.setOnClickListener{
            transaction.replace(R.id.contentContainer, HistoryScanFragment(), "CheckInFragment")
            transaction.addToBackStack("CheckInFragment").commit()
        }

        return view
    }

}