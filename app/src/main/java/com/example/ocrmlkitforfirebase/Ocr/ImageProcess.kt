package com.example.ocrmlkitforfirebase.Ocr

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ocrmlkitforfirebase.DB_MSSQL.ConnectionClass
import com.example.ocrmlkitforfirebase.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_image_process.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class ImageProcess : AppCompatActivity() {

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var editTitle: EditText
    private lateinit var btnTxtRecognize: Button
    private lateinit var btnTxtCopy: Button

    private lateinit var obj_ocr_images : Ocr_images
    private var path_image = ""

        data class Ocr_images (
        var imagePath : String? = "",
        var imageTitle : String? = "",
        var imageText : String? = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_process)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var action = supportActionBar
        action!!.title = "Image Scan"

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        editTitle = findViewById(R.id.editTitle)
        btnTxtRecognize = findViewById(R.id.btnTxtRecognize)
        btnTxtCopy = findViewById(R.id.btnTxtCopy)

        btnTxtRecognize.visibility = View.GONE

        val typeProcess = intent.getStringExtra("typeProcess")

        if(typeProcess == "camera"){
            EasyImage.openCameraForImage(this, 0)
        }else if(typeProcess == "gallery"){
            EasyImage.openGallery(this, 0)
        }

        btnTxtRecognize.setOnClickListener {
            startRecognizing()
        }

        btnTxtCopy.setOnClickListener {
            val textToCopy = editText.text
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
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
                btnTxtRecognize.visibility = View.VISIBLE
            }
        }
    }

    private fun loadImage(imageFile : String?) {
        path_image = imageFile!!
        Glide.with(this)
            .load(imageFile)
            .into(imageView)
    }

    private fun startRecognizing() {
        btnTxtRecognize.visibility = View.GONE
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
        btnTxtCopy.visibility = View.VISIBLE
        if (resultText.textBlocks.size == 0) {
            editText.setText("No Text Found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text
            editText.append(blockText + "\n")
        }
        ConnectionClass.db_img_ocr.ctx = this
        ConnectionClass.db_img_ocr.insertImagesOcr(editTitle?.text.toString(), editText?.text.toString())
        uploadImageDB()
    }

    private fun uploadImageDB(){
        var file = Uri.fromFile(File(path_image))
        val ref = storageReference?.child("images/${file.lastPathSegment}")
        val uploadTask = ref?.putFile(file)
        val urlTask = uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result

                val mRootRef = FirebaseDatabase.getInstance().getReference()
                val mMessagesRef = mRootRef.child("Images")
                if(editTitle.text.toString() == "" || editTitle.text.toString() == null){
                    editTitle.setText("no title image")
                }
                obj_ocr_images = Ocr_images(
                    downloadUri.toString(),
                    editTitle.text.toString(),
                    editText.text.toString()
                )
                mMessagesRef.push().setValue(obj_ocr_images)
                Toast.makeText(this, "Scan Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Handle failures", Toast.LENGTH_SHORT).show()
            }
        }
    }

}