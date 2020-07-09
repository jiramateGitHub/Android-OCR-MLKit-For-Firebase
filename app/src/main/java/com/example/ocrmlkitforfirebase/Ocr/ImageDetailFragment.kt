package com.example.ocrmlkitforfirebase.Ocr

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.bumptech.glide.Glide
import com.example.ocrmlkitforfirebase.LibraryFragment
import com.example.ocrmlkitforfirebase.R

class ImageDetailFragment : Fragment() {

    private lateinit var obj_ocr_images: Ocr_images
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var editTitle: EditText
    private lateinit var btnTxtCopy: Button

    data class Ocr_images(
        var imagePath: String? = "",
        var imageTitle: String? = "",
        var imageText: String? = ""
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)

        imageView = view.findViewById(R.id.imageView)
        editText = view.findViewById(R.id.editText)
        editTitle = view.findViewById(R.id.editTitle)
        btnTxtCopy = view.findViewById(R.id.btnTxtCopy)

        Glide.with(activity!!.baseContext)
            .load(obj_ocr_images.imagePath)
            .into(imageView)

        editText.setText(obj_ocr_images.imageText)
        editTitle.setText(obj_ocr_images.imageTitle)

        btnTxtCopy.setOnClickListener {
            val textToCopy = editText.text
            val clipboardManager  : ClipboardManager = activity!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(activity!!, "Text copied to clipboard", Toast.LENGTH_LONG).show()
        }

        return view
    }

    fun newInstance(imagePath: String, imageTitle: String, imageText: String): ImageDetailFragment {
        val fragment = ImageDetailFragment()
        val bundle = Bundle()
        bundle.putString("imagePath", imagePath)
        bundle.putString("imageTitle", imageTitle)
        bundle.putString("imageText", imageText)
        fragment.setArguments(bundle)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            obj_ocr_images = Ocr_images(
                bundle.getString("imagePath").toString(),
                bundle.getString("imageTitle").toString(),
                bundle.getString("imageText").toString()
            )
        }
    }
}