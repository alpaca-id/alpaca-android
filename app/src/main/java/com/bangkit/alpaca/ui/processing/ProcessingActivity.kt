package com.bangkit.alpaca.ui.processing

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.alpaca.R
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

@AndroidEntryPoint
class ProcessingActivity : AppCompatActivity() {

    private val processingViewModel: ProcessingViewModel by viewModels()

    private val tflite by lazy {
        Interpreter(loadModelFile("ocr_model.tflite"))
    }
    var output = TensorBuffer.createFixedSize(intArrayOf(2, 3200), DataType.FLOAT32)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing)

        // Upload image to the database
        val imageFile = intent.getSerializableExtra(EXTRA_IMAGE) as File
        val imageBitmap = BitmapFactory.decodeFile(imageFile.path)
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(32, 1280, ResizeOp.ResizeMethod.BILINEAR))
            .build()
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(imageBitmap)
        tensorImage = imageProcessor.process(tensorImage)

        val chars = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,*&!@~():`^]';|-"

        tflite.run(tensorImage.buffer, output.buffer)

        Log.d(TAG, "onCreate: ${output.dataType}")
        Log.d(TAG, "onCreate: ${output.floatArray.toList()}")
        Log.d(TAG, "onCreate: ${output.floatArray.toList().size}")

        tflite.close()

        // processingViewModel.uploadImage(picture)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun loadModelFile(path: String): MappedByteBuffer {
        val fileDescriptor = assets.openFd(path)

        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel

        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        private const val TAG = "ProcessingActivity"
    }
}