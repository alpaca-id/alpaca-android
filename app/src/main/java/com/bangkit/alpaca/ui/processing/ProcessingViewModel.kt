package com.bangkit.alpaca.ui.processing

import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.alpaca.data.StoryRepository
import com.bangkit.alpaca.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ProcessingViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    private var _stringResultPredict = MutableLiveData<String?>(null)
    val stringResultPredict: LiveData<String?> get() = _stringResultPredict

    private var _storyToEdit = MutableLiveData<Story?>(null)
    val storyToEdit: LiveData<Story?> get() = _storyToEdit

    fun predictImage(context: Context, imageFile: File) {
        viewModelScope.launch {
            val tflite = Interpreter(loadModelFile(context, "ocr_model.tflite"))
            val output = TensorBuffer.createFixedSize(intArrayOf(80, 80), DataType.FLOAT32)
            val chars =
                " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,*&!@~():`^]';|-"

            val imageBitmap = BitmapFactory.decodeFile(imageFile.path)
            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(32, 1280, ResizeOp.ResizeMethod.BILINEAR))
                .build()
            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(imageBitmap)
            tensorImage = imageProcessor.process(tensorImage)

            // Run image prediction with tflite
            tflite.run(tensorImage.buffer, output.buffer)

            // Convert result to 80x80 list
            val result = arrayListOf<List<Float>>()
            var currentIndex = 0
            for (i in 0 until 80) {
                val row = mutableListOf<Float>()
                for (j in 0 until 80) {
                    row.add(output.floatArray[currentIndex])
                    currentIndex++
                }

                result.add(row)
            }

            // Build the string result
            val stringBuilder = StringBuilder()
            result.forEach { charPred ->
                val max = charPred.indexOf(charPred.maxOrNull())
                stringBuilder.append(chars[max])
            }

            // Set stringResultPredict value
            _stringResultPredict.postValue(stringBuilder.toString())

            // Close the tflite
            tflite.close()
        }
    }

    fun setStoryToEdit(story: Story) {
        _storyToEdit.value = story
    }

    private fun loadModelFile(context: Context, path: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(path)

        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel

        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    /**
     * Save new story to the database
     *
     * @param story Story
     */
    fun saveNewStory(story: Story) {
        viewModelScope.launch {
            storyRepository.saveNewStory(story)
        }
    }

    fun updateStory(story: Story) {
        viewModelScope.launch {
            storyRepository.updateStory(story)
        }
    }
}