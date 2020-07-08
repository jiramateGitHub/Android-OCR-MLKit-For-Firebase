package com.example.ocrmlkitforfirebase.Ocr

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.ocrmlkitforfirebase.LibraryFragment
import com.example.ocrmlkitforfirebase.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_image_process.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class ImageProcess : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var editText: EditText
    lateinit var btnTxtRecognize: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_process)

        val typeProcess = intent.getStringExtra("typeProcess")

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        btnTxtRecognize = findViewById(R.id.btnTxtRecognize)

        if(typeProcess == "camera"){
            EasyImage.openCameraForImage(this, 0)
        }else if(typeProcess == "gallery"){
            EasyImage.openGallery(this, 0)
        }

        btnTxtRecognize.setOnClickListener {
            startRecognizing()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagesPicked(imageFiles: MutableList<File>, source: EasyImage.ImageSource?, type: Int) {
                CropImage.activity(Uri.fromFile(imageFiles[0])).start(this@ImageProcess)
            }
        })

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val result = CropImage.getActivityResult(data)
                loadImage(result.uri.path)
            }
        }
    }

    private fun loadImage(imageFile : String?) {
        Glide.with(this)
            .load(imageFile)
            .into(imageView)
    }

    private fun startRecognizing() {
        progressBar.visibility = View.VISIBLE
        if (imageView.drawable != null) {
            editText.setText("")
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    processResultText(firebaseVisionText)
                }
                .addOnFailureListener {
                    editText.setText("Failed")
                }
        }
    }

    private fun processResultText(resultText: FirebaseVisionText) {
        progressBar.visibility = View.GONE
        if (resultText.textBlocks.size == 0) {
            editText.setText("No Text Found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text
            editText.append(blockText + "\n")
        }


    }

}