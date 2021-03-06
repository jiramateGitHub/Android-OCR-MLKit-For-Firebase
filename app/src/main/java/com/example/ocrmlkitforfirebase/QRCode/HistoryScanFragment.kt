package com.example.ocrmlkitforfirebase.QRCode

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrmlkitforfirebase.DB_MSSQL.ConnectionClass
import com.example.ocrmlkitforfirebase.MainActivity
import com.example.ocrmlkitforfirebase.R

class HistoryScanFragment : Fragment() {
    private lateinit var btn_qrcode: Button
    lateinit var rv : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_history_scan, container, false)

        (activity as MainActivity).supportActionBar!!.title = "Scan QR Code"

        rv = view.findViewById(R.id.recyLayout)
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        ConnectionClass.db_img_qr.connectionClass = ConnectionClass()
        ConnectionClass.db_img_qr.ctx = requireContext()
        ConnectionClass.db_img_qr.getImagesQrCode(rv)

        btn_qrcode = view.findViewById(R.id.btn_qrcode)

        btn_qrcode.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CameraScan::class.java)
                it.startActivity(intent)
            }
        }

        return view
    }

}