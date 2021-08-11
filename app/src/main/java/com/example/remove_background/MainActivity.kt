package com.example.remove_background

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.content.Context
import android.content.res.AssetManager
import android.widget.Button
import android.widget.ImageView
import org.tensorflow.lite.support.model.Model


import androidx.appcompat.app.AppCompatActivity
import com.example.remove_background.ml.RemoveBG




import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity(){


    lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private val pickImage = 100

    val BATCH_SIZE = 1
    val IMG_SIZE_X = 320
    val IMG_SIZE_Y = 320
    val PIXEL_SIZE = 3
    val BYTES_PER_POINT = 4
    val MODEL_FILENAME = "removeBG.tflite"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing Views
        imageView = findViewById(R.id.imageView)


        var load_button = findViewById<Button>(R.id.open_image)
        var removebg = findViewById<Button>(R.id.bgremove)

        // input shape: { 1 x 320 (width) x 320 (height) x 3 (rgb) x 4 (float32) }
        var byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(BYTES_PER_POINT * BATCH_SIZE * PIXEL_SIZE * IMG_SIZE_X * IMG_SIZE_Y)


        fun loadModelFile(assets: AssetManager, modelFilename: String): MappedByteBuffer {
            val fileDescriptor = assets.openFd(modelFilename)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }


        removebg.setOnClickListener {

//            val options: Model.Options =
//                Model.Options.Builder().build()




//            val model = RemoveBg.newInstance(this)
//
//// Creates inputs for reference.
//            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3, 320, 320), DataType.FLOAT32)
//            inputFeature0.loadBuffer(byteBuffer)
//
//// Runs model inference and gets result.
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//// Releases model resources if no longer used.
//            model.close()
        }
////
        load_button.setOnClickListener {


            val modelFile = loadModelFile(assets, MODEL_FILENAME)


            //val ctx: Context = this

            //val model = RemoveBG.newInstance(ctx)





          // model = RemoveBg.newInstance(this)
//            val model = RemoveBg.newInstance(this)
            Log.d("print", "this line is being executed at the moment")

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.getData()
            //Displaying image in imageView
            imageView.setImageURI(imageUri)
        }
    }
}