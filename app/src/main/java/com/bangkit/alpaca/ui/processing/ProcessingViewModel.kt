package com.bangkit.alpaca.ui.processing

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*

class ProcessingViewModel : ViewModel() {

    private var _uploadedImageUrl = MutableLiveData<String>()
    val uploadedImageUrl: LiveData<String> get() = _uploadedImageUrl

    fun uploadImage(file: File) {
        val uri = Uri.fromFile(file)
        val storageRef = Firebase.storage.reference
        val imageRef =
            storageRef.child("image-scanning/${Firebase.auth.currentUser?.email}/${Calendar.getInstance().timeInMillis}.jpg")

        imageRef.putFile(uri).also { uploadTask ->
            uploadTask.addOnFailureListener {
                Firebase.crashlytics.apply {
                    log("Error when uploading an image")
                    recordException(it)
                }
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { url ->
                    _uploadedImageUrl.postValue(url.toString())
                }
            }
        }
    }
}